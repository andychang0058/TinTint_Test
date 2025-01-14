package com.weihua.tintinttest.api.base

import com.weihua.tintinttest.api.jsonplaceholder.JsonPlaceHolderApiCallAdapter
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.jvm.java

class ApiCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (returnType !is ParameterizedType) {
            error("Response must be parameterized as Result<Foo> or Result<out Foo>")
        }
        if (Call::class.java != getRawType(returnType)) {
            error("Response should be a Retrofit Call, check is suspend function used?")
        }

        val responseType = getParameterUpperBound(0, returnType)
        return when (retrofit.baseUrl().toString()) {
            ApiDomainContract.JSON_PLACEHOLDER -> JsonPlaceHolderApiCallAdapter<Any>(responseType)
            else -> throw RuntimeException("Unexpected base url, should add corresponding CallAdapter.")
        }
    }
}