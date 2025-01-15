package com.weihua.tintinttest.repo

import com.weihua.tintinttest.api.jsonplaceholder.JsonPlaceholderApi
import com.weihua.tintinttest.api.jsonplaceholder.model.Photo
import com.weihua.tintinttest.dao.PhotoDao
import com.weihua.tintinttest.dao.toPhoto
import com.weihua.tintinttest.dao.toPhotoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
class PhotoRepoImpl(
    private val api: JsonPlaceholderApi,
    private val dao: PhotoDao,
) : PhotoRepo {

    override suspend fun getPhotos(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Photo>>> = flow {
        runCatching {
            val startId = ((page - 1) * pageSize) + 1
            val photos = dao.getPhotosByPage(startId, pageSize)

            if (photos.isNotEmpty()) {
                emit(Result.success(photos.map { it.toPhoto() }))
                return@flow
            }

            api.getPhotos().fold(
                onSuccess = { photos ->
                    dao.insertAll(photos.map { it.toPhotoEntity() })
                    emit(Result.success(photos.subList(startId - 1, startId + pageSize - 1)))
                },
                onFailure = { error ->
                    emit(Result.failure(error))
                }
            )
        }.onFailure {
            emit(Result.failure(it))
        }
    }
}