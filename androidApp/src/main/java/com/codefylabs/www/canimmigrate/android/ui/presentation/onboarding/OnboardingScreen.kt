package com.codefylabs.www.canimmigrate.android.ui.presentation.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.codefylabs.www.canimmigrate.android.R
import com.codefylabs.www.canimmigrate.android.ui.components.base.OnEvent
import com.codefylabs.www.canimmigrate.android.ui.components.base.toast
import com.codefylabs.www.canimmigrate.android.ui.presentation.onboarding.components.OnboardingDataProvider
import com.codefylabs.www.canimmigrate.android.ui.presentation.onboarding.components.SurveyContainer
import com.codefylabs.www.canimmigrate.android.ui.theme.LinkBlue
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingEvent
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingSharedVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


const val ONBOARDING_NAV_ROUTE = "ONBOARDING_NAV_ROUTE"

fun NavHostController.navigateToOnBoarding(navOptions: NavOptions? = null) {
    navigate(route = ONBOARDING_NAV_ROUTE, navOptions = navOptions)
}

fun NavGraphBuilder.onboardingScreenRoute(
    onBackPressed: () -> Unit,
) {
    composable(route = ONBOARDING_NAV_ROUTE) {
        OnboardingScreen(onBackPressed = onBackPressed)
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun OnboardingScreen(
    onBackPressed: () -> Unit,
    viewModel: OnboardingSharedVM = koinViewModel(),
    onboardingDataProvider: OnboardingDataProvider = koinInject(),
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    OnEvent(event = viewModel.event) {
        when (it) {
            is OnboardingEvent.Error -> context.toast(it.error)
            is OnboardingEvent.Success -> onBackPressed()
        }
    }

    val coroutine = rememberCoroutineScope()
    val pagerState = rememberPagerState { state.onboardingSteps.size }
    var surveyContainers by remember {
        mutableStateOf<List<SurveyContainer>>(emptyList())
    }

    var currentSurveyContainer by remember {
        mutableStateOf<SurveyContainer?>(null)
    }

    val selectedSurveyContainers = remember {
        mutableStateListOf<SurveyContainer>()
    }

    LaunchedEffect(key1 = state.onboardingSteps) {
        surveyContainers =
            onboardingDataProvider.surveyContainers.filter { state.onboardingSteps.contains(it.onboardingStep) }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        currentSurveyContainer = surveyContainers.getOrNull(pagerState.currentPage)
    }

    fun skip() {
        coroutine.launch {
            pagerState.scrollToPage(pagerState.currentPage + 1)
        }
    }

    fun next() {
        coroutine.launch {
            currentSurveyContainer?.let {
                viewModel.submit(it.onboardingStep, it.description, it.selectedOption)

                if (pagerState.currentPage == surveyContainers.size - 1) {
                    viewModel.save()
                } else {
                    pagerState.scrollToPage(pagerState.currentPage + 1)
                }
            }
        }
    }

    fun onSelect(surveyContainer: SurveyContainer) {
        coroutine.launch(Dispatchers.IO) {
            currentSurveyContainer = surveyContainer
            val list = selectedSurveyContainers.toMutableList()
            list.removeAll { it.onboardingStep == surveyContainer.onboardingStep }
            list.add(surveyContainer)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = surveyContainers.getOrNull(pagerState.currentPage)?.title ?: "",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }, navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }, actions = {
                TextButton(onClick = {
                    skip()
                }) {
                    Text(text = "Skip", color = LinkBlue)
                }
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                userScrollEnabled = false,
                key = {
                    surveyContainers.getOrNull(it)?.onboardingStep?.name
                        ?: (Int.MIN_VALUE..Int.MAX_VALUE).random()
                }) {
                var selectedOption by remember { mutableStateOf("Select") }

                surveyContainers.getOrNull(it)?.let { pageData ->
                    PageContent(pageData = pageData, selectedOption = selectedOption, onSelect = {
                        selectedOption = it
                        onSelect(pageData.copy(selectedOption = it))
                    })
                }
            }

            Button(
                onClick = {
                    next()
                },
                enabled = !currentSurveyContainer?.selectedOption.isNullOrEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Continue")
            }

            PagerIndicator(pageCount = surveyContainers.size, currentPage = pagerState.currentPage)
        }
    }
}

@Composable
fun PageContent(pageData: SurveyContainer, selectedOption: String, onSelect: (String) -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = pageData.imageName),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 30.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        DropdownMenu(options = pageData.options, onSelect = {
            onSelect(it)
        }, selectedOption = selectedOption)
    }
}


@Composable
fun DropdownMenu(options: List<String>, selectedOption: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val screenWidth = LocalConfiguration.current.screenWidthDp


    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
                containerColor = Color.Transparent,
            ),
            border = BorderStroke(0.5.dp, color = Color.LightGray.copy(0.7f))
        ) {
            Text(
                text = selectedOption, style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }


        androidx.compose.material3.DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width((screenWidth - 36).dp)
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it)
                    },
                    onClick = {
                        onSelect(it)
                        expanded = false
                    },
                )
            }
        }
    }
}


@Composable
fun PagerIndicator(pageCount: Int, currentPage: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .padding(2.dp)
                    .background(
                        color = if (index == currentPage) MaterialTheme.colorScheme.primary else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}

