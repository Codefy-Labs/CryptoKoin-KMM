package com.codefylabs.www.cryptokoin.settings.presentation

import com.codefylabs.www.cryptokoin.core.util.Event
import com.codefylabs.www.cryptokoin.core.util.State
import com.codefylabs.www.cryptokoin.core.util.StateViewModel
import com.codefylabs.www.cryptokoin.settings.domain.models.AppPreference
import com.codefylabs.www.cryptokoin.settings.domain.usecases.AppPreferenceUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingSharedViewModel(
    private val appPreferenceUseCase: AppPreferenceUseCase
) : StateViewModel<SettingEvent, SettingState>(SettingState.initial()) {

    init {
        coroutine.launch {
            appPreferenceUseCase.getAppPreferencesAsFlow().collectLatest {
                updateState(state.value.copy(appPreference = it))
            }
        }
    }

    suspend fun toggleDarkMode() {
        appPreferenceUseCase.updatePreferences(state.value.appPreference.copy(isDarkMode = !state.value.appPreference.isDarkMode))
    }

}


data class SettingState(
    val appPreference: AppPreference = AppPreference()
) : State {
    companion object {
        fun initial() = SettingState()
    }

}

sealed class SettingEvent : Event {
    data class Error(val error: String) : SettingEvent()
    data class ShowSuccessMessage(val message: String) : SettingEvent()

}