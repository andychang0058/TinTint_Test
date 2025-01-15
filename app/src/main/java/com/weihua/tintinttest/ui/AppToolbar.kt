package com.weihua.tintinttest.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.weihua.tintinttest.R
import com.weihua.tintinttest.ui.theme.TinTintTestTheme

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    showNavigation: Boolean = false,
    onNavigationClicked: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title
            )
        },
        navigationIcon = {
            if (showNavigation) {
                IconButton(onClick = { onNavigationClicked() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
    )
}

@PreviewLightDark
@Composable
fun HomeToolbarPreview() {
    TinTintTestTheme {
        Surface {
            AppToolbar(
                title = stringResource(R.string.app_name),
                showNavigation = true,
            )
        }
    }
}