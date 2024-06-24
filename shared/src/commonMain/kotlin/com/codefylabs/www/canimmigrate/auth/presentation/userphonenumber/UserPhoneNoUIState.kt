package com.codefylabs.www.canimmigrate.auth.presentation.userphonenumber

import com.codefylabs.www.canimmigrate.core.util.Event
import com.codefylabs.www.canimmigrate.core.util.State
import com.codefylabs.www.canimmigrate.core.util.countryPicker.CountryInfo

data class UserPhoneNoViewState(
    val countryInfoList: List<CountryInfo> = CountryInfo.countryInfoList,
    val isSendingOtp: Boolean = false,
    val isVerifyingOtp: Boolean = false,
    val isOtpSent: Boolean = false,
    val phoneNumber: String = "",
    val dialCode: String = "+1",
    val otp: String = "",
) : State {
    companion object {
        fun initial() = UserPhoneNoViewState()
    }
}

sealed class UserPhoneNoEvent : Event {
    data class OnOtpSent(val message: String) : UserPhoneNoEvent()
    data class OnOtpVerified(val message: String) : UserPhoneNoEvent()
    data class Error(val error: String) : UserPhoneNoEvent()

}
