package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.CoroutineTestRule
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListUsersTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var userRepository : UserRepository

    private lateinit var listUsers: ListUsers

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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        listUsers = ListUsers(userRepository)
    }

    @Test
    fun `when get users you will get flow List User`() = runTest {

        // given
        val flowList: Flow<List<User>> = flow { list }
        coEvery { userRepository.getUsers() } returns flowList

        // when
        val result = listUsers.getUsers()

        // then
        assertEquals(flowList, result)
//        coVerify { userRepository.getUsers() }

    }
}