package com.example.hmtestcode.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hmtestcode.domain.model.Swatches

@Composable
fun ColorSwatches(swatches: List<Swatches>) {

    Row {
        val visible = swatches.take(3)

        visible.forEach { swatch ->
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(
                        Color(android.graphics.Color.parseColor(swatch.colorCode))
                    )
            )
        }

        if (swatches.size > 3) {
            Text(text = " +${swatches.size - 3}")
        }
    }
}