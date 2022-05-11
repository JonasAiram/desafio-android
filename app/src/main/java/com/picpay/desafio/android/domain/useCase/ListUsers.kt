package com.picpay.desafio.android.domain.useCase

import com.picpay.desafio.android.domain.repository.UserRepository

class ListUsers(private val userRepository: UserRepository) {

    suspend fun getUsers() = userRepository.getUsers()


}