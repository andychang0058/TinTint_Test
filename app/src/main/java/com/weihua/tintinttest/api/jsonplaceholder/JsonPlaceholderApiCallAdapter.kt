package com.weihua.tintinttest.api.jsonplaceholder

import com.weihua.tintinttest.api.base.ResultCall
import com.weihua.tintinttest.api.base.toResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class JsonPlaceHolderApiCallAdapter<T>(
    val responseType: Type
) : CallAdapter<T, Call<Result<T>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Call<Result<T>> {
        return ResultCall(
            delegate = call,
            convertBody = { response ->
                response.toResult(responseType = responseType, convertErrorBody = null)
            }
        )
    }
}