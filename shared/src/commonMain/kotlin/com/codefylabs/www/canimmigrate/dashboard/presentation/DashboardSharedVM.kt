package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.auth.domain.usescases.LocalDataUseCase
import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import kotlinx.coroutines.launch


class DashboardSharedVM(
    private val localDataUseCase: LocalDataUseCase,
) : StateViewModel<DashboardEvent, DashboardViewState>(DashboardViewState.initial()) {


    init {
        fetchSurvey()
    }

    private fun fetchSurvey() {
        coroutine.launch {
            if (!localDataUseCase.getLocalData().isLaunchOnboardingFinished) {
                sendEvent(DashboardEvent.OpenOnboardingSurvey)
            }
        }
    }

}

sealed class DashboardEvent : Event {
    data class Success(val message: String) : DashboardEvent()
    data object OpenOnboardingSurvey : DashboardEvent()
    data class Error(val error: String) : DashboardEvent()
}

data class DashboardViewState(val isLoading: Boolean = false) : State {
    companion object {
        fun initial() = DashboardViewState()
    }
}


