package com.weihua.tintinttest.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.weihua.tintinttest.R
import com.weihua.tintinttest.common.LoadState
import com.weihua.tintinttest.common.UiState

fun <T> LazyGridScope.loadMoreUiState(
    uiState: UiState<T>,
    isEmpty: Boolean,
    onRetry: () -> Unit,
) {
    when (uiState.state) {
        is LoadState.Success -> {
            if (uiState.state.isReachedEnd && isEmpty.not()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    LoadNoMoreData()
                }
            }
        }

        is LoadState.Loading -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadMoreProgress()
            }
        }

        is LoadState.LoadError -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                LoadMoreError(onRetry = onRetry)
            }
        }

        else -> Unit
    }
}

fun <T> LazyGridScope.refreshUiState(
    uiState: UiState<T>,
    onRetry: () -> Unit,
) {
    when (uiState.state) {
        is LoadState.RefreshError -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                ScreenError(
                    modifier = Modifier.fillMaxSize(),
                    message = stringResource(R.string.error_occurred),
                    onRetry = onRetry,
                )
            }
        }

        else -> Unit
    }
}