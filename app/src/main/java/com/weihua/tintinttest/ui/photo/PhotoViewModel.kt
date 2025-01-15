package com.weihua.tintinttest.ui.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weihua.tintinttest.api.jsonplaceholder.model.Photo
import com.weihua.tintinttest.common.LoadState
import com.weihua.tintinttest.common.UiState
import com.weihua.tintinttest.repo.PhotoRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PhotoViewModel(
    private val repo: PhotoRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState<List<Photo>>())
    val uiState: StateFlow<UiState<List<Photo>>> = _uiState

    private var page = PhotoRepo.INITIAL_PAGE
    private var isReachedEnd = false

    private var photoCollectJob: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        page = PhotoRepo.INITIAL_PAGE
        isReachedEnd = false

        _uiState.update {
            it.copy(state = LoadState.Refresh)
        }

        photoCollectJob?.cancel()
        photoCollectJob = viewModelScope.launch(Dispatchers.IO) {
            repo.getPhotos(page = page)
                .conflate()
                .collect { result ->
                    if (result.isFailure) {
                        val throwable = result.exceptionOrNull() ?: Exception("Unknown error")
                        _uiState.update {
                            it.copy(state = LoadState.RefreshError(throwable))
                        }
                        return@collect
                    }

                    val photos = result.getOrNull() ?: emptyList()
                    isReachedEnd = photos.size < PhotoRepo.PAGE_SIZE

                    _uiState.update {
                        it.copy(
                            data = photos,
                            state = LoadState.Success(isReachedEnd = isReachedEnd)
                        )
                    }
                }
        }
    }

    fun retry() {
        _uiState.update {
            it.copy(data = null)
        }
        refresh()
    }

    fun loadMore(isRetry: Boolean = false) {
        if (isReachedEnd) {
            return
        }

        if (_uiState.value.state is LoadState.Loading) {
            return
        }

        _uiState.update {
            it.copy(state = LoadState.Loading)
        }
        page = if (isRetry) page else page + 1

        viewModelScope.launch(Dispatchers.IO) {
            repo.getPhotos(page = page)
                .conflate()
                .collect { result ->
                    result
                        .onFailure { error ->
                            _uiState.update {
                                it.copy(state = LoadState.LoadError(error))
                            }
                        }.onSuccess { photos ->
                            isReachedEnd = photos.size < PhotoRepo.PAGE_SIZE

                            _uiState.update {
                                it.copy(
                                    data = it.data?.plus(photos),
                                    state = LoadState.Success(isReachedEnd = isReachedEnd)
                                )
                            }
                        }
                }
        }
    }
}