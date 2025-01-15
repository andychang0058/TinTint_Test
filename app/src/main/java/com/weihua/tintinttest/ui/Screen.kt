package com.weihua.tintinttest.ui

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Home

    @Serializable
    data object Photo
}
