package com.example.hmtestcode

import android.app.Application
import com.example.hmtestcode.di.appModule
import com.example.hmtestcode.di.networkModule
import com.example.hmtestcode.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class HmTestCodeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HmTestCodeApplication)
            modules(
                appModule,
                repositoryModule,
                networkModule
            )
        }
    }
}