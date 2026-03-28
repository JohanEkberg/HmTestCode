package com.example.hmtestcode.di

import com.example.hmtestcode.data.remote.api.HmApi
import com.example.hmtestcode.data.remote.api.HmProductClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    // Retrofit
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.hm.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // API interface
    single<HmApi> {
        get<Retrofit>().create(HmApi::class.java)
    }

    // Client wrapper
    single { HmProductClient(get()) }
}