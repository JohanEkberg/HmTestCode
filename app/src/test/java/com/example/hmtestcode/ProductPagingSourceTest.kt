package com.example.hmtestcode

import androidx.paging.PagingSource
import com.example.hmtestcode.data.remote.api.HmApi
import com.example.hmtestcode.data.remote.dto.PaginationDto
import com.example.hmtestcode.data.remote.dto.PriceDto
import com.example.hmtestcode.data.remote.dto.ProductDto
import com.example.hmtestcode.data.remote.dto.ProductResponseDto
import com.example.hmtestcode.data.remote.dto.SearchHitsDto
import com.example.hmtestcode.data.remote.dto.SwatchesDto
import com.example.hmtestcode.data.remote.paging.ProductPagingSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ProductPagingSourceTest  {

    private val api = mockk<HmApi>()

    fun fakeProductResponse(
        page: Int,
        totalPages: Int
    ): ProductResponseDto {
        return ProductResponseDto(
            searchHits = SearchHitsDto(
                productList = listOf(
                    ProductDto(
                        productName = "A",
                        prices = listOf(
                            PriceDto(formattedPrice = "10.00kr"),
                            PriceDto(formattedPrice = "20.00kr")
                        ),
                        swatches = listOf(
                            SwatchesDto(
                                colorName = "Red",
                                colorCode = "#FF0000",
                                productImage = "https://example.com/image1.jpg"
                            ),
                            SwatchesDto(
                                colorName = "Green",
                                colorCode = "#000000",
                                productImage = "https://example.com/image1.jpg"
                            )
                        )

                    ),
                    ProductDto(
                        productName = "B",
                        prices = listOf(
                            PriceDto(formattedPrice = "100.00kr"),
                            PriceDto(formattedPrice = "200.00kr")
                        ),
                        swatches = listOf(
                            SwatchesDto(
                                colorName = "Red",
                                colorCode = "#FF0000",
                                productImage = "https://example.com/image1.jpg"
                            ),
                            SwatchesDto(
                                colorName = "Green",
                                colorCode = "#000000",
                                productImage = "https://example.com/image1.jpg"
                            )
                        )
                    )
                )
            ),
            pagination = PaginationDto(
                currentPage = page,
                totalPages = totalPages,
                nextPageNum = page + 1
            )
        )
    }

    @Test
    fun `load sets prevKey to null on first page`() = runTest {
        val fakeResponse = fakeProductResponse(page = 1, totalPages = 3)

        coEvery { api.searchProducts(any(), any(), 1) } returns fakeResponse

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(1, 20, false)
        )

        val page = result as PagingSource.LoadResult.Page

        assertNull(page.prevKey)
    }

    @Test
    fun `load sets prevKey correctly on page 2`() = runTest {
        val fakeResponse = fakeProductResponse(page = 2, totalPages = 3)

        coEvery { api.searchProducts(any(), any(), 2) } returns fakeResponse

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(2, 20, false)
        )

        val page = result as PagingSource.LoadResult.Page

        assertEquals(1, page.prevKey)
    }

    @Test
    fun `load sets nextKey to null on last page`() = runTest {
        val fakeResponse = fakeProductResponse(page = 3, totalPages = 3)

        coEvery { api.searchProducts(any(), any(), 3) } returns fakeResponse

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(3, 20, false)
        )

        val page = result as PagingSource.LoadResult.Page

        assertNull(page.nextKey)
    }

    @Test
    fun `load returns error on IOException`() = runTest {
        coEvery { api.searchProducts(any(), any(), any()) } throws IOException()

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(1, 20, false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `load returns error on HttpException`() = runTest {
        val exception = HttpException(
            Response.error<Any>(500, "".toResponseBody(null))
        )

        coEvery { api.searchProducts(any(), any(), any()) } throws exception

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(1, 20, false)
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `load returns empty list when all products are invalid`() = runTest {
        val invalidResponse = fakeProductResponse(page = 1, totalPages = 3)
            .copy(
                searchHits = SearchHitsDto(
                    productList = listOf(
                        ProductDto(
                            productName = "",
                            prices = emptyList(),
                            swatches = emptyList()
                        )
                    )
                )
            )

        coEvery { api.searchProducts(any(), any(), 1) } returns invalidResponse

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(1, 20, false)
        )

        val page = result as PagingSource.LoadResult.Page

        assertTrue(page.data.isEmpty())
    }

    @Test
    fun `load defaults to page 1 when key is null`() = runTest {
        val fakeResponse = fakeProductResponse(page = 1, totalPages = 3)

        coEvery { api.searchProducts(any(), any(), 1) } returns fakeResponse

        val pagingSource = ProductPagingSource(api)

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page

        assertEquals(2, page.nextKey)
    }
}