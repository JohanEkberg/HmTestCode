package com.example.hmtestcode.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.hmtestcode.ui.components.EmptyView
import com.example.hmtestcode.ui.components.ErrorView
import com.example.hmtestcode.ui.components.LoadingView
import com.example.hmtestcode.ui.components.ProductList
import com.example.hmtestcode.ui.components.ScrollToTopFab
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val products = viewModel.products.collectAsLazyPagingItems()

    LaunchedEffect(products.loadState, products.itemCount) {
        viewModel.onLoadStateChanged(
            products.loadState,
            products.itemCount
        )
    }

    LaunchedEffect(products.loadState.append) {
        viewModel.onAppendStateChanged(products.loadState.append)
    }

    // Using LazyListState to track scroll position
    val listState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {

        ProductList(listState = listState, products = products, uiState = uiState)

        if (uiState.isLoading) {
            LoadingView()
        }

        uiState.error?.let { uiText ->
            ErrorView(
                message = stringResource(
                    id = uiText.resId,
                    *uiText.args.toTypedArray()
                ),
                onRetry = { products.retry() },
                onDismiss = {}
            )
        }

        if (uiState.isEmpty) {
            EmptyView()
        }

        ScrollToTopFab(listState = listState, products = products)
    }
}