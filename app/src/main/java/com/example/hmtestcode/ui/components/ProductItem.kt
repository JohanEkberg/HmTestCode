package com.example.hmtestcode.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.hmtestcode.domain.model.Product

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {

        val imageUrl = product.swatches.first().productImage

        ImageView(imageUrl = imageUrl, name = product.name)

        Text(
            text = product.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(text = product.price.first())

        ColorSwatches(swatches = product.swatches)
    }
}