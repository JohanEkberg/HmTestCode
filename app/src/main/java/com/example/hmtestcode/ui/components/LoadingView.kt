package com.example.hmtestcode.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.hmtestcode.R

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    // This will just show a text when loading, not nice especially when used in a list,
    // but it'll do for now.
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(stringResource(R.string.load_products))
    }
}