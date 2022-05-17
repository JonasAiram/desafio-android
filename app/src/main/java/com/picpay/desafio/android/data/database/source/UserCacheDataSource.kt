package com.picpay.desafio.android.data.database.source

import com.picpay.desafio.android.data.database.dao.UserDAO
import com.picpay.desafio.android.data.database.model.UserCache
import kotlinx.coroutines.flow.Flow


interface UserCacheDataSource {
    fun getUsers(): Flow<List<UserCache>>
    fun insertData(userCache: List<UserCache>)
    fun updateData(cacheList: List<UserCache>)
}

class UserCacheDataSourceImlp(private val userDAO: UserDAO): UserCacheDataSource {

    override fun getUsers(): Flow<List<UserCache>> = userDAO.getUsers()

    override fun insertData(userCache: List<UserCache>) {
        userDAO.insertAll(userCache)
    }

    override fun updateData(cacheList: List<UserCache>) {
        userDAO.updateData(cacheList)
    }

}