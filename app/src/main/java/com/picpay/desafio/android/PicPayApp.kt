package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.di.PicPayModule
import com.picpay.desafio.android.domain.di.DomainModule
import com.picpay.desafio.android.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PicPayApp)
        }

        PicPayModule.load()
        PresentationModule.load()
        DomainModule.load()
    }
}