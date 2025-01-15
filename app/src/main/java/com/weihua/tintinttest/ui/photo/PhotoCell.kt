package com.weihua.tintinttest.ui.photo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.weihua.tintinttest.ui.theme.TinTintTestTheme

@Composable
fun PhotoCell(
    id: String,
    title: String,
    thumbnailUrl: String,
) {
    Box(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f / 1f),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            model = ImageRequest.Builder(LocalContext.current)
                .data(thumbnailUrl)
                .build(),
            contentDescription = "Cover Image",
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = id,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@PreviewLightDark
@Composable
fun PhotoCellPreview() {
    TinTintTestTheme {
        Surface {
            PhotoCell(
                id = "1",
                title = "Photo 1",
                thumbnailUrl = "https://via.placeholder.com/150"
            )
        }
    }
}