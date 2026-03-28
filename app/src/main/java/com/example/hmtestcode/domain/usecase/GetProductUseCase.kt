package com.example.hmtestcode.domain.usecase

import androidx.paging.PagingData
import com.example.hmtestcode.data.repository.ProductRepository
import com.example.hmtestcode.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<PagingData<Product>> {
        return repository.getProducts()
    }
}