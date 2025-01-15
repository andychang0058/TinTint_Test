package com.weihua.tintinttest.ui.photo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weihua.tintinttest.R
import com.weihua.tintinttest.api.jsonplaceholder.model.Photo
import com.weihua.tintinttest.common.LoadState
import com.weihua.tintinttest.common.UiState
import com.weihua.tintinttest.ui.common.ScreenError
import com.weihua.tintinttest.ui.common.loadMoreUiState
import com.weihua.tintinttest.ui.common.refreshUiState
import com.weihua.tintinttest.ui.common.ObserveBottomReached
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoScreen() {
    val viewModel: PhotoViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyGridState = rememberLazyGridState()

    lazyGridState.ObserveBottomReached(
        columns = 4,
        prefetchDistance = 10,
    ) {
        viewModel.loadMore(isRetry = false)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.state is LoadState.Refresh,
        onRefresh = {
            viewModel.refresh()
        }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            state = lazyGridState,
        ) {
            photos(photos = uiState.data)

            refreshUiState(
                uiState = uiState,
                onRetry = { viewModel.retry() }
            )
            loadMoreUiState(
                uiState = uiState,
                isEmpty = uiState.data.isNullOrEmpty(),
                onRetry = { viewModel.loadMore(isRetry = true) }
            )
        }

        EmptyUiState(
            uiState = uiState,
            onRetry = { viewModel.loadMore(isRetry = true) }
        )

        PullRefreshIndicator(
            refreshing = uiState.state is LoadState.Refresh,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun EmptyUiState(uiState: UiState<List<Photo>>, onRetry: () -> Unit) {
    if (uiState.state !is LoadState.Success || uiState.data.isNullOrEmpty().not()) {
        return
    }
    ScreenError(
        modifier = Modifier.fillMaxSize(),
        message = stringResource(R.string.empty_data),
        onRetry = onRetry,
    )
}

private fun LazyGridScope.photos(photos: List<Photo>? = null) {
    items(
        count = photos?.size ?: 0,
        key = { index -> photos?.getOrNull(index)?.id.toString() },
        contentType = { index -> photos?.getOrNull(index)?.javaClass }
    ) { index ->
        val photo = photos?.getOrNull(index) ?: return@items
        PhotoCell(
            id = photo.id.toString(),
            title = photo.title,
            thumbnailUrl = photo.thumbnailUrl,
        )
    }
}

