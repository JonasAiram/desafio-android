package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.CoroutineTestRule
import com.picpay.desafio.android.data.database.model.UserCache
import com.picpay.desafio.android.data.database.source.UserCacheDataSource
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.domain.entities.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @get:Rule
    val coroutineTestRule: CoroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var picPayService: PicPayService
    @MockK
    private lateinit var userCacheDataSource: UserCacheDataSource

    private lateinit var userRepositoryImpl: UserRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        userRepositoryImpl = UserRepositoryImpl(picPayService, userCacheDataSource)
    }

    @Test
    fun `When user cache is expired return api users`() = runTest {

        //given
        val userList: List<User> =  listOf(
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
        val cacheList: List<UserCache> =  listOf(
            UserCache(
                id = 1,
                name = "name_valid_1",
                img = "imagem",
                username = "username",
                updateDate = 1652958864000
            ),
            UserCache(
                id = 2,
                name = "name_valid_2",
                img = "imagem",
                username = "username",
                updateDate = 1652958864000
            )
        )
        coEvery { userCacheDataSource.getUsers() } returns flow { emit(cacheList) }
        coEvery { userCacheDataSource.updateData(any()) } returns Unit
        coEvery { picPayService.getUsers() } returns Response.success(userList)

        //when
        val teste = userRepositoryImpl.getUsers().single()

        //then
        assertEquals(userList, teste)
//        verify { userCacheDataSource.getUsers() }

    }

    @Test
    fun `When user cache is not expired, return room users`() = runTest {

        //given
        val userList: List<User> =  listOf(
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
        val cacheList: List<UserCache> =  listOf(
            UserCache(
                id = 1,
                name = "name_valid_1",
                img = "imagem",
                username = "username",
                updateDate = System.currentTimeMillis()
            ),
            UserCache(
                id = 2,
                name = "name_valid_2",
                img = "imagem",
                username = "username",
                updateDate = System.currentTimeMillis()
            )
        )
        coEvery { userCacheDataSource.getUsers() } returns flow { emit(cacheList) }
        coEvery { userCacheDataSource.updateData(any()) } returns Unit
        coEvery { picPayService.getUsers() } returns Response.success(userList)

        //when
        val teste = userRepositoryImpl.getUsers().single()

        //then
        assertEquals(userList, teste)
//        verify { userCacheDataSource.getUsers() }
    }

    @Test
    fun `When cache is empty, returns API user list`() = runTest {

        //given
        val userList: List<User> =  listOf(
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
        val cacheList: List<UserCache> =  listOf()
        coEvery { userCacheDataSource.getUsers() } returns flow { emit(cacheList) }
        coEvery { userCacheDataSource.updateData(any()) } returns Unit
        coEvery { picPayService.getUsers() } returns Response.success(userList)

        //when
        val teste = userRepositoryImpl.getUsers().single()

        //then
        assertEquals(userList, teste)
//        verify { userCacheDataSource.getUsers() }
    }

//    @TODO testar quando falhar a requisição pela internet



}