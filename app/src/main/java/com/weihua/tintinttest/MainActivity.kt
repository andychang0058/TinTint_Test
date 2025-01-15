package com.weihua.tintinttest

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weihua.tintinttest.ui.TinTintTestApp
import com.weihua.tintinttest.ui.common.isSystemInDarkTheme
import com.weihua.tintinttest.ui.theme.TinTintTestTheme
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isDarkTheme by mutableStateOf(isSystemInDarkTheme)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isSystemInDarkThemeFlow().onEach { isDarkTheme = it }
                    .distinctUntilChanged()
                    .collect {
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                Color.TRANSPARENT,
                                Color.TRANSPARENT
                            ) { isDarkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                // The default color defined by EdgeToEdge.kt
                                Color.argb(0xe6, 0xFF, 0xFF, 0xFF),
                                Color.argb(0x80, 0x1b, 0x1b, 0x1b),
                            ) { isDarkTheme }
                        )
                    }
            }
        }
        setContent {
            TinTintTestTheme {
                TinTintTestApp(isDarkTheme = isDarkTheme)
            }
        }
    }

    private fun isSystemInDarkThemeFlow() = callbackFlow<Boolean> {
        trySend(isSystemInDarkTheme)
        val listener = Consumer<Configuration> {
            channel.trySend(isSystemInDarkTheme)
        }
        addOnConfigurationChangedListener(listener)
        awaitClose { removeOnConfigurationChangedListener(listener) }
    }
}
