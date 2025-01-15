package com.weihua.tintinttest.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weihua.tintinttest.R

@Composable
fun HomeScreen(
    onButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onButtonClick) {
            Text(
                text = stringResource(R.string.click_me),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}