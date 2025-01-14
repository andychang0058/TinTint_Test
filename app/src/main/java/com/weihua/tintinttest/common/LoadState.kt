package com.weihua.tintinttest.common

sealed class LoadState {
    data object Refresh : LoadState()
    data object Loading : LoadState()
    data class Success(val isReachedEnd: Boolean = false) : LoadState()
    data class RefreshError(val error: Throwable) : LoadState()
    data class LoadError(val error: Throwable) : LoadState()
}