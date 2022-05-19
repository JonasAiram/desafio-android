package com.picpay.desafio.android.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.CoroutineTestRule
import com.picpay.desafio.android.TestableObserver
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.useCase.ListUsers
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var listUsers: ListUsers

    private lateinit var viewModel: MainViewModel

    val exception = Exception()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(listUsers, coroutineTestRule.testDispatcherProvider)
    }

    private val list: List<User> = listOf(
        User(
            id = 1,
            name = "name_valid_1",
            img = "imagem",
            username = "username"
        ),
        User(
            id = 2,
            name = "name_valid_2",
            img = "imagem",
            username = "username"
        )
    )

    @Test
    fun `Check the get user list flow`() = runTest {

        coEvery { listUsers.getUsers() } returns flow { emit(list) }

        val stateObserver = TestableObserver<MainViewModel.State>()

        viewModel.users.apply {
            observeForever(stateObserver)
        }

        viewModel.listUsers()
        stateObserver.assertAllEmitted(
            listOf(
                MainViewModel.State.Loading,
                MainViewModel.State.Success(list)
            )
        )
    }

    @Test
    fun `Check user list flow with any exception`() = runTest {

        coEvery { listUsers.getUsers() } returns flow { throw exception }

        val stateObserver = TestableObserver<MainViewModel.State>()

        viewModel.users.apply {
            observeForever(stateObserver)
        }

        viewModel.listUsers()
        stateObserver.assertAllEmitted(
            listOf(
                MainViewModel.State.Loading,
                MainViewModel.State.Error(exception)
            )
        )
    }

}
