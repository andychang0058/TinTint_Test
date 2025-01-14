package com.weihua.tintinttest.api.base

import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import kotlin.run

fun ResponseBody?.cloneString(): String? {
    if (this == null) return null
    return try {
        source().run {
            request(Long.MAX_VALUE) // Buffer the entire body.
            buffer.clone().readString(Charsets.UTF_8)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun <T> Response<T>.toResult(
    responseType: Type,
    convertErrorBody: ((String) -> Any?)? = null
): Result<T> {
    return if (isSuccessful) {
        val body: T? = body()
        if (body is Unit) {
            return Result.success(Unit as T)
        }

        if (body == null) {
            return Result.failure(
                ApiException.ResponseBodyNull(
                    message = "Declared type is $responseType but received body is null"
                )
            )
        }
        Result.success(body)

    } else {
        val message = message()
        val code = code()
        val errorBody = errorBody()?.cloneString()?.let {
            convertErrorBody?.invoke(it)
        }

        val apiException: ApiException = when (code) {
            400 -> ApiException.BadRequest(message, errorBody)
            401 -> ApiException.Unauthorized(message, errorBody)
            403 -> ApiException.Forbidden(message, errorBody)
            404 -> ApiException.NotFound(message, errorBody)
            429 -> ApiException.TooManyRequests(message, errorBody)
            500 -> ApiException.InternalServerError(message, errorBody)
            else -> ApiException.Unknown(message, code)
        }
        Result.failure(apiException)
    }
}