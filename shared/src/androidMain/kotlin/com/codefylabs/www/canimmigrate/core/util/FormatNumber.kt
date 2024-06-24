package com.codefylabs.www.canimmigrate.core.util

import com.google.i18n.phonenumbers.PhoneNumberUtil
// androidMain source set
import java.text.NumberFormat
import java.util.Locale

actual fun formatPhoneNumber(phoneNumber: String): String {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return try {
        val numberProto = phoneUtil.parse(phoneNumber, "US")
        phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    } catch (e: Exception) {
        phoneNumber
    }
}



actual fun Double.asCurrencyWith2Decimals(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.maximumFractionDigits = 2
    format.minimumFractionDigits = 2
    return format.format(this)
}