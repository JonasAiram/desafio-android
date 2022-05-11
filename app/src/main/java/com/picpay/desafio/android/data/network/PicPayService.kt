package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.domain.entities.User
import retrofit2.Response
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}