package com.weihua.tintinttest.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val json = Json {
    encodeDefaults = true
    explicitNulls = false
    isLenient = true
    ignoreUnknownKeys = true
    coerceInputValues = true
}