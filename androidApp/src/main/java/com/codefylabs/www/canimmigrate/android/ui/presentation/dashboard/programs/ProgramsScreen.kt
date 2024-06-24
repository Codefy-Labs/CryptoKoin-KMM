package com.codefylabs.www.canimmigrate.android.ui.presentation.dashboard.programs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.ActionButton
import com.codefylabs.www.canimmigrate.android.ui.components.navigation.TopBar
import com.codefylabs.www.canimmigrate.android.ui.theme.AppTheme
import com.codefylabs.www.canimmigrate.dashboard.domain.models.VisaCategory

const val PROGRAMS_NAV_ROUTE = "PROGRAMS_NAV_ROUTE"


fun NavHostController.navigateToProgramsScreen(navOptions: NavOptions? = null) {
    navigate(route = PROGRAMS_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.programsScreenRoute() {
    composable(route = PROGRAMS_NAV_ROUTE) {
        ProgramsScreen()
    }
}

@Preview
@Composable
private fun PreviewProgramsScreen() {
    AppTheme {
        Surface {
            ProgramsScreen()
        }
    }
}

@Composable
private fun ProgramsScreen() {
    val lazyColumnState = rememberLazyListState()
    Scaffold(topBar = {
        TopBar(title = "Programs", actions = {
            ActionButton(imageResId = R.drawable.ic_bell, onClick = {})
        })
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = lazyColumnState,
            contentPadding = PaddingValues(16.dp)
        ) {

            item {
                Text(
                    text = "Visa Categories", style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            items(items = VisaCategory.visaCategories) {
                Card(
                    modifier = Modifier.padding(vertical = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onBackground.copy(0.9f),
                        contentColor = MaterialTheme.colorScheme.background
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp, horizontal = 22.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {

                            Text(
                                text = it.title, style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it.description,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = it.title,
                            tint = Color.Red
                        )
                    }
                }
            }

        }
    }
}



