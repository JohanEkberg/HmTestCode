package com.example.hmtestcode.data.remote.dto

data class ProductResponseDto(
    val pagination: PaginationDto,
    val searchHits: SearchHitsDto
)

data class PaginationDto(
    val currentPage: Int,
    val nextPageNum: Int?,
    val totalPages: Int
)

data class SearchHitsDto(
    val productList: List<ProductDto>
)

data class ProductDto(
    val productName: String,
    val prices: List<PriceDto>,
    val swatches: List<SwatchesDto>,
)

data class PriceDto(
    val formattedPrice: String
)

data class SwatchesDto(
    val colorName: String,
    val colorCode: String,
    val productImage: String
)