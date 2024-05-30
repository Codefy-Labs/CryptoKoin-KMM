package com.codefylabs.www.spider

import com.codefylabs.www.spider.core.util.Event
import com.codefylabs.www.spider.core.util.State
import com.codefylabs.www.spider.core.util.StateViewModel
import com.codefylabs.www.spider.settings.domain.usecases.AppPreferenceUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AppSharedViewModel(
    private val appPreferenceUseCase: AppPreferenceUseCase,
) : StateViewModel<AppEvent, AppState>(AppState.init()) {

    init {
        coroutine.launch {
            appPreferenceUseCase.getAppPreferencesAsFlow().collectLatest {
                updateState(newState = state.value.copy(isDarkMode = it.isDarkMode))
            }
        }
    }

}


sealed interface AppEvent : Event {

}

data class AppState(
    val isDarkMode: Boolean = false,
) : State {

    companion object {
        fun init() = AppState()
    }

}