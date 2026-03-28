package com.example.hmtestcode.di

import com.example.hmtestcode.domain.usecase.GetProductsUseCase
import com.example.hmtestcode.ui.screens.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { GetProductsUseCase(get()) }

    viewModel { ProductViewModel(get()) }
}