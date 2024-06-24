package com.codefylabs.www.canimmigrate.dashboard.presentation

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.dashboard.domain.models.VisaCategory

class ProgramsSharedVM(
) : StateViewModel<ProgramsEvent, ProgramsViewState>(ProgramsViewState.initial()) {


}

sealed class ProgramsEvent : Event {
    data class Success(val message: String) : ProgramsEvent()
    data class Error(val error: String) : ProgramsEvent()
}

data class ProgramsViewState(
    val isLoading: Boolean = false,
    val visaCategories: List<VisaCategory> = VisaCategory.visaCategories,
) : State {
    companion object {
        fun initial() = ProgramsViewState()
    }
}


