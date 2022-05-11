package com.picpay.desafio.android.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.network.PicPayService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object PicPayModule {

    fun load() {
        loadKoinModules(networkModules())
    }

    private fun networkModules(): Module {
    return module {
        single {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
        }

        single {
            GsonConverterFactory.create(GsonBuilder().create())
        }

        single {
            createdService<PicPayService>(get(), get())
        }
    }
}
    private inline fun <reified T> createdService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }

}