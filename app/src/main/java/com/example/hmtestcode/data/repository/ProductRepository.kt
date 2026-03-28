package com.example.hmtestcode.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.hmtestcode.data.remote.api.HmProductClient
import com.example.hmtestcode.data.remote.paging.ProductPagingSource

class ProductRepository(
    private val client: HmProductClient
) {

    fun getProducts() = Pager(
        config = PagingConfig(
            pageSize = 20,
            prefetchDistance = 4, // Number of pages to prefetch
            enablePlaceholders = false
        ),
        pagingSourceFactory = { ProductPagingSource(client.api) }
    ).flow
}