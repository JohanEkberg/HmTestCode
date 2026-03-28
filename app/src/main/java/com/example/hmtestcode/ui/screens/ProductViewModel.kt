package com.example.hmtestcode.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.example.hmtestcode.R
import com.example.hmtestcode.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.HttpException
import java.io.IOException

class ProductViewModel(
    private val useCase: GetProductsUseCase
) : ViewModel() {

    // Get the products from the use case
    val products = useCase()
        .cachedIn(viewModelScope)

    private val _uiState = MutableStateFlow(ProductUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    // Handle load state changes
    fun onLoadStateChanged(loadState: CombinedLoadStates, itemCount: Int) {
        val refresh = loadState.refresh

        _uiState.update {
            when (refresh) {
                is LoadState.Loading -> {
                    it.copy(
                        isLoading = true,
                        error = null,
                        isEmpty = false
                    )
                }

                is LoadState.Error -> {
                    it.copy(
                        isLoading = false,
                        error = refresh.error.toUiTextResourceId(),
                        isEmpty = false
                    )
                }

                is LoadState.NotLoading -> {
                    it.copy(
                        isLoading = false,
                        error = null,
                        isEmpty = itemCount == 0
                    )
                }
            }
        }
    }

    // Handle append state changes
    fun onAppendStateChanged(append: LoadState) {
        _uiState.update {
            when (append) {
                is LoadState.Loading -> it.copy(isAppending = true, appendError = null)
                is LoadState.Error -> it.copy(
                    isAppending = false,
                    appendError = append.error.toUiTextResourceId()
                )
                is LoadState.NotLoading -> it.copy(isAppending = false, appendError = null)
            }
        }
    }
}

data class UiText(
    val resId: Int,
    val args: List<Any> = emptyList()
)

fun Throwable.toUiTextResourceId(): UiText {
    return when (this) {
        is IOException -> UiText(R.string.error_no_internet)

        is HttpException -> UiText(
            R.string.error_server_error,
            listOf(code())
        )

        else -> UiText(R.string.error_unknown)
    }
}