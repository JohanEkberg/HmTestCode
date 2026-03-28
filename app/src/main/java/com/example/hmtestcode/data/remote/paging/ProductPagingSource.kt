package com.example.hmtestcode.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hmtestcode.data.remote.api.HmApi
import com.example.hmtestcode.data.remote.mapper.toDomainOrNull
import com.example.hmtestcode.domain.model.Product
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val api: HmApi
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            // No key, start with page 1
            val page = params.key ?: 1

            // Get response for current page
            val response = api.searchProducts(page = page)

            // Map response to domain model
            val products = response.searchHits.productList.mapNotNull { it.toDomainOrNull() }

            val nextKey = if (page < response.pagination.totalPages) {
                page + 1
            } else {
                null
            }

            LoadResult.Page(
                data = products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        // Find page to be reloaded at refresh
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}