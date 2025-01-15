package com.weihua.tintinttest.ui.common

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
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