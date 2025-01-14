package com.weihua.tintinttest.common

data class UiState<T>(
    val state: LoadState = LoadState.Refresh,
    val data: T? = null,
)