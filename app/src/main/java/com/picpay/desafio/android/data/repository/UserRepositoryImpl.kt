package com.picpay.desafio.android.data.repository

import android.util.Log
import com.picpay.desafio.android.data.database.mapper.UserCacheMapper
import com.picpay.desafio.android.data.database.model.UserCache
import com.picpay.desafio.android.data.database.source.UserCacheDataSource
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.*

//class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {

class UserRepositoryImpl(
    private val picPayService: PicPayService,
    private val userCacheDataSource: UserCacheDataSource
) : UserRepository {

    val EXPIRED_TIME: Long = 60000 // 1 minute

    override suspend fun getUsers() : Flow<List<User>> {

        return userCacheDataSource.getUsers()
            .map { cacheList ->
                val result = when {
                    cacheList.isEmpty() -> getUsersNetwork()
                    else -> getUsersCacheExpired(cacheList)
                }
                result
            }
    }

    private suspend fun getUsersNetwork(): List<User> {

        try {
            val response = picPayService.getUsers()
            Log.e("UserRepository", "getUsersNetwork")
            if (!response.isSuccessful) {
                throw Exception()
            }

            val cacheList = response.body()?.let { UserCacheMapper.mapUsersToCache(it) }
            if (cacheList != null) {
                userCacheDataSource.updateData(cacheList)
            }

            return (response.body()?: listOf())
        } catch (exception: Exception) {
            Log.e("UserRepository", exception.toString())
            throw exception
        }

    }

    private suspend fun getUsersCacheExpired(cacheList : List<UserCache>): List<User> {
        if (cacheList[0].updateDate > System.currentTimeMillis() - EXPIRED_TIME){
            val debug = cacheList[0].updateDate - (System.currentTimeMillis() - EXPIRED_TIME)
            Log.e("UserRepository", "return cache $debug milisegundos")
            return UserCacheMapper.map(cacheList)
        }
        Log.e("UserRepository", "return network")
        return getUsersNetwork()
    }
}