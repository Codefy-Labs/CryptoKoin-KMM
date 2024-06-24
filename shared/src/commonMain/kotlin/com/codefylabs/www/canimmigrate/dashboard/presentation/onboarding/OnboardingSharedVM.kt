package com.codefylabs.www.canimmigrate.dashboard.presentation.onboarding

import com.codefylabs.www.canimmigrate.auth.domain.entities.Survey
import com.codefylabs.www.canimmigrate.auth.domain.usescases.LocalDataUseCase
import com.codefylabs.www.canimmigrate.auth.domain.usescases.SessionUseCase
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import kotlinx.coroutines.launch


class OnboardingSharedVM(
    private val session: SessionUseCase,
    private val localDataUseCase: LocalDataUseCase,
) : StateViewModel<OnboardingEvent, OnboardingState>(OnboardingState.initial()) {

    private val answers: MutableSet<Survey> = mutableSetOf()

    init {
        coroutine.launch {
            val updatedSteps = mutableListOf<OnboardingStep>()
            if (session.isLoggedIn()) {
                if (!localDataUseCase.getLocalData().isLaunchOnboardingFinished) {
                    updatedSteps.addAll(
                        listOf(
                            OnboardingStep.IMMIGRATION_STATUS,
                            OnboardingStep.PATHWAY,
                            OnboardingStep.TARGET_PROVINCE
                        )
                    )
                }
                updatedSteps.addAll(
                    listOf(
                        OnboardingStep.LANGUAGE_PROFICIENCY,
                        OnboardingStep.EMPLOYMENT_STATUS,
                        OnboardingStep.OCCUPATION_INDUSTRY,
                        OnboardingStep.EDUCATION,
                        OnboardingStep.FAMILY_STATUS,
                        OnboardingStep.HOUSING_PREFERENCES,
                        OnboardingStep.SETTLEMENT_SERVICES,
                        OnboardingStep.TRANSPORTATION_NEEDS
                    )
                )
            } else {
                updatedSteps.addAll(
                    listOf(
                        OnboardingStep.IMMIGRATION_STATUS,
                        OnboardingStep.PATHWAY,
                        OnboardingStep.TARGET_PROVINCE
                    )
                )
            }

            updateState(state.value.copy(onboardingSteps = updatedSteps))
        }
    }

    fun submit(onboardingStep: OnboardingStep, question: String, answer: String) {
        answers.add(Survey(onboardingStep, question, answer))
    }

    fun save() {
        coroutine.launch {
            updateState(state.value.copy(isLoading = true))
            when (val result = localDataUseCase.saveOnOnboardingSurvey(answers.toList())) {
                is Either.Error -> {
                    if (result.message.trim().isNotEmpty())
                        sendEvent(OnboardingEvent.Error(result.message))
                }
                is Either.Success -> sendEvent(OnboardingEvent.Success(result.data))
            }
        }
    }
}

sealed class OnboardingEvent : Event {
    data class Success(val message: String) : OnboardingEvent()
    data class Error(val error: String) : OnboardingEvent()
}

data class OnboardingState(
    val isLoading: Boolean = false,
    val onboardingSteps: List<OnboardingStep> = emptyList(),
) : State {
    companion object {
        fun initial() = OnboardingState()
    }
}

