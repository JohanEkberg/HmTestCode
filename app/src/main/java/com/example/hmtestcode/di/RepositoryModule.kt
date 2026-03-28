package com.example.hmtestcode.di

import com.example.hmtestcode.data.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { ProductRepository(get()) }
}