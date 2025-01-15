package com.weihua.tintinttest.ui

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.weihua.tintinttest.R
import com.weihua.tintinttest.ui.theme.TinTintTestTheme

@Composable
fun TinTintTestApp(isDarkTheme: Boolean) {
    TinTintTestTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()

        val backStackEntryState by navController.currentBackStackEntryAsState()
        val currentDestination = backStackEntryState?.destination

        val showNavigation by remember(currentDestination) {
            derivedStateOf {
                currentDestination?.hasRoute<Screen.Home>() == false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal))
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppToolbar(
                        title = stringResource(R.string.app_name),
                        showNavigation = showNavigation,
                        onNavigationClicked = { navController.navigateUp() },
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable<Screen.Home>(
                        exitTransition = null,
                        popEnterTransition = null,
                    ) {

                    }
                    composable<Screen.Photo>(
                        enterTransition = { enterTransition },
                        exitTransition = null,
                        popEnterTransition = null,
                        popExitTransition = { exitTransition }
                    ) {

                    }
                }
            }
        }
    }
}

private val enterTransition = slideInHorizontally(
    initialOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(300)
)

private val exitTransition = slideOutHorizontally(
    targetOffsetX = { fullWidth -> fullWidth },
    animationSpec = tween(300)
)