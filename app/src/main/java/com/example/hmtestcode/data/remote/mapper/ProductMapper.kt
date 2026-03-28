package com.example.hmtestcode.data.remote.mapper

import com.example.hmtestcode.data.remote.dto.ProductDto
import com.example.hmtestcode.domain.model.Product
import com.example.hmtestcode.domain.model.Swatches

fun ProductDto.toDomainOrNull(): Product? {
    if (productName.isBlank()) return null

    if (prices.isEmpty() || prices.any { it.formattedPrice.isBlank() }) return null

    val validSwatches = swatches.mapNotNull {
        val safeColorCode = ensureHashPrefix(it.colorCode)
        if (!isValidHexColor(safeColorCode)) return@mapNotNull null
        if (!isValidUrl(it.productImage)) return@mapNotNull null
        Swatches(
            colorName = it.colorName,
            colorCode = safeColorCode,
            productImage = it.productImage
        )
    }

    if (validSwatches.isEmpty()) return null

    return Product(
        name = productName,
        price = prices.map { it.formattedPrice },
        swatches = validSwatches
    )
}

private fun ensureHashPrefix(colorCode: String): String {
    val trimmed = colorCode.trim()
    return if (trimmed.startsWith("#")) {
        trimmed
    } else {
        "#$trimmed"
    }
}

private fun isValidHexColor(colorCode: String): Boolean {
    return colorCode.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$"))
}

private fun isValidUrl(url: String): Boolean {
    return url.startsWith("http://") || url.startsWith("https://")
}