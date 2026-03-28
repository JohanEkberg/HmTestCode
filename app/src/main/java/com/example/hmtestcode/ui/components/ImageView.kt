package com.example.hmtestcode.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.hmtestcode.R

@Composable
fun ImageView(imageUrl: String, name: String) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = name,
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
    ) {

        when (painter.state) {

            is AsyncImagePainter.State.Loading -> {
                Text(stringResource(R.string.load_image))
            }

            is AsyncImagePainter.State.Error -> {
                Text(stringResource(R.string.error_load_image_failed))
            }

            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}