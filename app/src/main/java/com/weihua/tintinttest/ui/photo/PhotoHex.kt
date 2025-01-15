package com.weihua.tintinttest.ui.photo

import android.net.Uri
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity

@Composable
fun BoxWithConstraintsScope.rememberHexPlaceholderUrl(
    hex: String,
    textHex: String = "FFFFFF80",
): String {
    val density = LocalDensity.current
    val (widthPx, heightPx) = remember(density, maxWidth, maxHeight) {
        with(density) {
            maxWidth.toPx().toInt() to maxHeight.toPx().toInt()
        }
    }
    return remember(widthPx, heightPx, hex) {
        Uri.Builder().scheme("https")
            .authority("placehold.co")
            .appendPath("${widthPx}x$heightPx")
            .appendPath(hex)
            .appendPath(textHex)
            .build()
            .toString()
    }
}