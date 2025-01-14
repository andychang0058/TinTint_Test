package com.weihua.tintinttest.api.base

sealed class ApiException(
    override val message: String? = null,
    open val body: Any? = null
) : Exception(message) {

    data class BadRequest(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class Unauthorized(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class Forbidden(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class NotFound(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class TooManyRequests(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class ResponseBodyNull(
        override val message: String?,
    ) : ApiException(message, null)

    data class InternalServerError(
        override val message: String?,
        override val body: Any? = null
    ) : ApiException(message, body)

    data class ConnectionError(
        override val message: String?,
    ) : ApiException(message, null)

    data class ConnectionTimeout(
        override val message: String?,
    ) : ApiException(message, null)

    data class Unknown(
        override val message: String?,
        val code: Int
    ) : ApiException(message, null)

}