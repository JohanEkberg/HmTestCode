package com.example.hmtestcode.domain.model

data class Product(
    val name: String,
    val price: List<String>,
    val swatches: List<Swatches>
)

data class Swatches(
    val colorName: String,
    val colorCode: String,
    val productImage: String
)