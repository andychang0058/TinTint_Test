package com.weihua.tintinttest.ui.common

import android.content.Context
import android.content.res.Configuration

val Context.isSystemInDarkTheme: Boolean
    get() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
            Configuration.UI_MODE_NIGHT_YES