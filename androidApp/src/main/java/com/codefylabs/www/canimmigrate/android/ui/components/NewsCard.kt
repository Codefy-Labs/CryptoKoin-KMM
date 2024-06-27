package com.codefylabs.www.canimmigrate.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.theme.LinkBlue
import com.codefylabs.www.canimmigrate.dashboard.domain.models.Feed

@Composable
fun NewsCard(modifier: Modifier = Modifier, item: Feed, onClick: (Feed) -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(item.imageRes)
            .crossfade(true)
            .size(200)
            .build()
    )
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick(item)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Column {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp,
                        fontSize = 12.sp,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp),
                    minLines = 5,
                    maxLines = 5
                )
                Text(
                    text = "Read more",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        color = LinkBlue
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.daysToGo,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = "Views",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.size(12.dp)
                    )

                    Text(
                        text = item.views,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.padding(start = 6.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Shares",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = item.shares,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NewsCardWide(modifier: Modifier = Modifier, item: Feed, onClick: (Feed) -> Unit) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(item.imageRes)
            .crossfade(true)
            .size(100) // Use size to limit the image size
            .build()
    )

    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable {
                onClick(item)
            }
            .background(Color.LightGray.copy(0.2f), shape = MaterialTheme.shapes.small)
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        lineHeight = 14.sp,
                        fontSize = 12.sp,
                    ),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp),
                    minLines = 3,
                    maxLines = 3
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.daysToGo,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = "Views",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.size(12.dp)
                    )

                    Text(
                        text = item.views,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.padding(start = 6.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Shares",
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = item.shares,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 9.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))


            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp)
            )
        }
    }
}

@Preview
@Composable
private fun BlogItemUiPreview() {

}

