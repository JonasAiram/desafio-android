package com.picpay.desafio.android.data.repository

import android.util.Log
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val picPayService: PicPayService) : UserRepository {

    override suspend fun getUsers() = flow {
        try {
            val response = picPayService.getUsers()
            if (!response.isSuccessful) {
//                levantar exceção
            }
            emit(response.body())
        } catch (exception: Exception) {
            Log.e("UserRepository", exception.toString())
            throw exception
        }
    }

}