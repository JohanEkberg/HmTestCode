package com.example.hmtestcode.ui.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.hmtestcode.R
import com.example.hmtestcode.domain.model.Product
import com.example.hmtestcode.ui.screens.ProductUiState

@Composable
fun ProductList(
    listState: LazyListState,
    products: LazyPagingItems<Product>,
    uiState: ProductUiState
) {
    // Save the current scroll position
    LazyColumn(state = listState) {

        items(products.itemCount / 2 + products.itemCount % 2) { index ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
            ) {
                val first = products[index * 2]
                val second = products[index * 2 + 1]

                ProductCell(first)
                ProductCell(second)
            }
        }

        if (uiState.isAppending) {
            item { Text(stringResource(R.string.load_more)) }
        }

        uiState.appendError?.let { uiText ->
            item {
                ErrorView(
                    message = stringResource(
                        id = uiText.resId,
                        *uiText.args.toTypedArray()
                    ),
                    onRetry = { products.retry() },
                    onDismiss = {}
                )
            }
        }
    }
}

@Composable
private fun RowScope.ProductCell(product: Product?) {
    if (product != null) {
        ProductItem(
            product,
            Modifier
                .weight(1f)
                .padding(4.dp)
        )
    } else {
        Spacer(Modifier.weight(1f))
    }
}