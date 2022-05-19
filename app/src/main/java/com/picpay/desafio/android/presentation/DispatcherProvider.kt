package com.picpay.desafio.android.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}

class DefaultDispatchers : DispatcherProvider {
    override fun default() = Dispatchers.Default
    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO
}
