package com.codefylabs.www.canimmigrate.auth.presentation.userphonenumber

import com.codefylabs.www.canimmigrate.auth.domain.usescases.UserPhoneNoUseCase
import com.codefylabs.www.canimmigrate.core.util.Either
import com.codefylabs.www.canimmigrate.core.util.StateViewModel
import com.codefylabs.www.canimmigrate.core.util.Validator
import com.codefylabs.www.canimmigrate.core.util.countryPicker.CountryInfo
import kotlinx.coroutines.delay


class UserPhoneNoViewModel(
    private val useCase: UserPhoneNoUseCase,
) : StateViewModel<UserPhoneNoEvent, UserPhoneNoViewState>(UserPhoneNoViewState.initial()) {

    fun updatePhoneNo(phoneNo: String) {
        if (state.value.phoneNumber != phoneNo)
            updateState(state.value.copy(phoneNumber = phoneNo, isOtpSent = false))
    }

    fun updateCountryCode(country: CountryInfo) {
        updateState(state.value.copy(dialCode = country.dial_code))
    }

    fun updateOtp(otp: String) {
        updateState(state.value.copy(otp = otp))
    }

    suspend fun sendOtp() {
        val phoneNumber = state.value.dialCode.plus(state.value.phoneNumber)

        if (Validator.isValidPhoneNumber(phoneNumber)) {
            updateState(state.value.copy(isSendingOtp = true))
            when (val result = useCase.sendOtpOnUserPhoneNumber(phoneNumber)) {
                is Either.Error -> {
                    sendEvent(UserPhoneNoEvent.Error(result.message))
                    updateState(state.value.copy(isSendingOtp = false, isOtpSent = false))
                }

                is Either.Success -> {
                    sendEvent(UserPhoneNoEvent.OnOtpSent(result.data))
                    updateState(state.value.copy(isSendingOtp = false, isOtpSent = true))
                }
            }
        } else {
            sendEvent(UserPhoneNoEvent.Error("Please enter valid phone number."))
        }
    }


    suspend fun submitOtp() {
        val phoneNumber = state.value.dialCode.plus(state.value.phoneNumber)

        updateState(state.value.copy(isVerifyingOtp = true))
        when (val result = useCase.verifyUserPhoneNumber(
            phoneNumber = phoneNumber,
            otp = state.value.otp
        )) {
            is Either.Error -> {
                sendEvent(UserPhoneNoEvent.Error(result.message))
                updateState(state.value.copy(isVerifyingOtp = false))
            }

            is Either.Success -> {
                updateState(state.value.copy(isVerifyingOtp = false))
                sendEvent(UserPhoneNoEvent.OnOtpVerified("Phone Number Verified Successfully"))
            }
        }
    }

    suspend fun resendOtp() {
        updateState(state.value.copy(isSendingOtp = true))
        delay(2000)
        updateState(state.value.copy(isSendingOtp = false))
        sendEvent(UserPhoneNoEvent.OnOtpSent("Otp Sent Again"))
    }

}
