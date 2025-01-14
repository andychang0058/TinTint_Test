package com.weihua.tintinttest.api.base

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.getOrElse
import kotlin.mapCatching
import kotlin.runCatching

class ResultCall<T>(
    private val delegate: Call<T>,
    private val convertBody: (Response<T>) -> Result<T>,
) : Call<Result<T>> {

    override fun execute(): Response<Result<T>> {
        return runCatching { delegate.execute() }
            .mapCatching {
                Response.success(convertBody.invoke(it))
            }
            .getOrElse {
                val finalThrowable = when (it) {
                    is SocketTimeoutException -> ApiException.ConnectionTimeout(it.message)
                    is IOException -> ApiException.ConnectionError(it.message)
                    else -> it
                }
                Response.success(Result.Companion.failure(finalThrowable))
            }
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        val newCall = this
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val resultBody = convertBody.invoke(response)
                callback.onResponse(newCall, Response.success(resultBody))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val finalThrowable = when (t) {
                    is SocketTimeoutException -> ApiException.ConnectionTimeout(t.message)
                    is IOException -> ApiException.ConnectionError(t.message)
                    else -> t
                }
                callback.onResponse(
                    newCall,
                    Response.success(Result.Companion.failure(finalThrowable))
                )
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun clone(): Call<Result<T>> = this

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}