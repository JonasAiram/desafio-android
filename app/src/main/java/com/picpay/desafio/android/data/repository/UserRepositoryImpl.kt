package com.picpay.desafio.android.data.repository

import android.util.Log
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.domain.entities.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {

    override suspend fun getUsers() = flow<List<User>> {
        try {
            val response = picPayService.getUsers()
            if (!response.isSuccessful) {
                throw Exception()
            }
            emit(response.body()?: listOf())
        } catch (exception: Exception) {
            Log.e("UserRepository", exception.toString())
            throw exception
        }
    }

}