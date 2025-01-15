package com.weihua.tintinttest.di

import android.app.Application
import com.weihua.tintinttest.api.base.ApiDomainContract
import com.weihua.tintinttest.api.base.defaultOkhttpBuilder
import com.weihua.tintinttest.api.base.defaultRetrofitBuilder
import com.weihua.tintinttest.api.jsonplaceholder.JsonPlaceholderApi
import com.weihua.tintinttest.dao.PhotoDatabase
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.weihua.tintinttest")
class TinTintTestModule {
    @Single
    internal fun providerJsonPlaceholderApi(): JsonPlaceholderApi {
        return defaultRetrofitBuilder
            .baseUrl(ApiDomainContract.JSON_PLACEHOLDER)
            .client(
                defaultOkhttpBuilder.build()
            )
            .build()
            .create(JsonPlaceholderApi::class.java)
    }

    @Single
    internal fun providerPhotoDatabase(application: Application): PhotoDatabase {
        return PhotoDatabase.createInstance(application)
    }

    @Single
    fun providerPhotoDao(database: PhotoDatabase) = database.photoDao()
}