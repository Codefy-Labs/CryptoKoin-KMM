package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme

@Preview
@Composable
private fun PreviewExpressEntry() {
    AppTheme {
        Surface {
            ExpressEntrySection()
        }
    }
}

@Composable
fun ExpressEntrySection() {
    val lazyColumnState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(), state = lazyColumnState,
        contentPadding = PaddingValues(16.dp)
    ) {

        items(items = ExpressEntry.getExpressEntries()) {
            ExpressEntryRow(expressEntry = it)
        }

    }
}

@Composable
private fun ExpressEntryRow(expressEntry: ExpressEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .background(
                color = Color.Black.copy(0.9f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = expressEntry.imageResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            colorFilter = ColorFilter.tint(Color.Red)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = expressEntry.title, style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = expressEntry.subtitle, style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        )

    }
}

data class ExpressEntry(
    val title: String,
    val imageResId: Int,
    val subtitle: String,
) {
    companion object {
        fun getExpressEntries() = listOf(
            ExpressEntry("Overview", R.drawable.ic_search, "A Brief overview of the process"),
            ExpressEntry(
                "Pre-Application",
                R.drawable.ic_application,
                "A Brief overview of the process"
            ),
            ExpressEntry("Pre-ITA", R.drawable.ic_mail, "While you wait for your invitation"),
            ExpressEntry("Post-ITA", R.drawable.ic_mail_open, "Submitting your application"),
            ExpressEntry("Landing", R.drawable.ic_plane_landing, "After you arrive")
        )
    }
}