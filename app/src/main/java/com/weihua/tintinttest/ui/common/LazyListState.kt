package com.weihua.tintinttest.ui.common

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kotlin.collections.lastOrNull

@Composable
fun LazyGridState.ObserveBottomReached(
    columns: Int = 1,
    prefetchDistance: Int = 10,
    onReached: () -> Unit
) {
    val isReachedBottom by remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            val prefetchRow = (prefetchDistance / columns) + 1

            lastVisibleItem.index != 0 &&
                    lastVisibleItem.index >= layoutInfo.totalItemsCount - (prefetchRow * columns)
        }
    }

    LaunchedEffect(isReachedBottom) {
        if (isReachedBottom) {
            onReached()
        }
    }
}