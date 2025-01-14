package com.weihua.tintinttest.api.base

import com.weihua.tintinttest.api.json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

val defaultRetrofitBuilder: Retrofit.Builder
    get() = Retrofit.Builder()
        .addCallAdapterFactory(ApiCallAdapterFactory())
        .addConverterFactory(KotlinSerializationConverterFactory(json))

val defaultOkhttpBuilder: OkHttpClient.Builder
    get() = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            }
        )
