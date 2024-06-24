package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.process.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitizenshipSection() {
    val lazyColumnState = rememberLazyListState()
    val citizenshipSteps = listOf(
        "Citizenship Overview",
        "Apply for Citizenship",
        "Complete CIT forms for Citizenship",
        "Receive AOR after Submission",
        "Test date and Interview",
        "Oath Taken & Citizenship Ceremony",
        "Grant of Citizenship"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = citizenshipSteps) {
            Card(
                onClick = { },
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.padding(vertical = 12.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it, style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = it,
                        tint = MaterialTheme.colorScheme.onBackground.copy(0.4f)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun PreviewCitizenshipSection() {
    AppTheme {
        Surface {
            CitizenshipSection()
        }
    }
}