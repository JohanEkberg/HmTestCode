package com.example.hmtestcode.ui.screens

data class ProductUiState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val isEmpty: Boolean = false,
    val isAppending: Boolean = false,
    val appendError: UiText? = null
)