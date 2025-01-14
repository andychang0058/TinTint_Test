package com.weihua.tintinttest.api.jsonplaceholder

import com.weihua.tintinttest.api.jsonplaceholder.model.Photo
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("photos")
    suspend fun getPhotos(): Result<List<Photo>>
}
