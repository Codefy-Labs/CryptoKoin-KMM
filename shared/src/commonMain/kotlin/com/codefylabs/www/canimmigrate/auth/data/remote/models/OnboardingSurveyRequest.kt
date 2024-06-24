package com.codefylabs.www.canimmigrate.auth.data.remote.models

import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey

data class OnboardingSurveyRequest(
    val data: List<Survey>,
)