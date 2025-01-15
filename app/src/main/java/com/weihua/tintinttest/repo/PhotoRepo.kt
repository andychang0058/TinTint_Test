package com.weihua.tintinttest.repo

import com.weihua.tintinttest.api.jsonplaceholder.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepo {

    companion object {
        const val INITIAL_PAGE = 1
        const val PAGE_SIZE = 30
    }

    suspend fun getPhotos(page: Int, pageSize: Int = PAGE_SIZE): Flow<Result<List<Photo>>>

}