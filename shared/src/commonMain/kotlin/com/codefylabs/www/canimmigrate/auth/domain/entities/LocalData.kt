package com.codefylabs.www.canimmigrate.auth.domain.entities

import com.codefylabs.www.canimmigrate.auth.data.local.entity.LocalDataObject
import com.codefylabs.www.canimmigrate.auth.data.local.entity.SurveyDataObject
import com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding.OnboardingStep
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmObject
import kotlinx.serialization.Serializable

data class LocalData(
    val isLaunchOnboardingFinished: Boolean = false,
    val isDarkModelOn: Boolean = false,
    val onboardingSurvey: List<Survey>? = null,
)


@Serializable
data class Survey(
    val key: OnboardingStep,
    val question: String,
    val answer: String,
)

fun Survey.toSurveyObject() : SurveyDataObject = SurveyDataObject().also {
    it.key = this.key.name
    it.question = this.question
    it.answer = this.answer
}

fun LocalDataObject?.toLocalData(): LocalData = this?.let {
    LocalData(
        isLaunchOnboardingFinished = it.isLaunchOnboardingFinished,
        onboardingSurvey = it.onboardingSurvey?.map { survey ->
            Survey(
                key = OnboardingStep.valueOf(survey.key),
                answer = survey.answer,
                question = survey.question
            )
        }
    )
} ?: LocalData()

fun LocalData.toRealmObject(): RealmObject {
    val data = LocalDataObject()
    data.isLaunchOnboardingFinished = isLaunchOnboardingFinished
    data.onboardingSurvey = onboardingSurvey?.map { survey ->
        SurveyDataObject().also {
            it.key = survey.key.name
            it.question = survey.question
            it.answer = survey.answer
        }
    }?.toRealmList()
    return data
}