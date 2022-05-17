package com.picpay.desafio.android.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.MainCoroutineRule
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.useCase.ListUsers
import com.picpay.desafio.android.getOrAwaitTestValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @JvmField
    @Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    // Make sure viewModelScope uses a test dispatcher
    @get:Rule
    val coroutinesDispatcherRule = MainCoroutineRule()

    @MockK
    private lateinit var listUsers: ListUsers

    private lateinit var viewModel: MainViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MainViewModel(listUsers)
    }

    private val list: List<User> = listOf(
        User(
            id = 1,
            name ="name_valid_1",
            img = "imagem",
            username = "username"
        ),
        User(
            id = 2,
            name ="name_valid_2",
            img = "imagem",
            username = "username"
        )
    )

    @Test
    suspend fun `Check flow of get User`()  = runBlockingTest  {

//        given - dados fornecidos para simulador o teste
        coEvery { listUsers.getUsers() } returns flow { emit(list) }

//        when - quando o teste será simulado
        viewModel.listUsers()
        viewModel.users.getOrAwaitTestValue()

//        then - verificar
        Assert.assertEquals(MainViewModel.State.Loading::class.java, viewModel.users.value?.javaClass)

        viewModel.users.getOrAwaitTestValue()
        Assert.assertEquals(MainViewModel.State.Success::class.java, viewModel.users.value?.javaClass)

    }



//    @Test
//    suspend fun `Check flow of get User`() = testDispatcher.runBlockingTest {
//
//        val flow = flow {
//            emit(list)
//        }
//
//        `when`(listUsers.getUsers()).thenReturn(flow)
//
//        Assert.assertEquals(true, true)
//    }


}