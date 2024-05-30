package com.codefylabs.www.spider.core.util


import cocoapods.libPhoneNumber_iOS.NBEPhoneNumberFormatINTERNATIONAL
import cocoapods.libPhoneNumber_iOS.NBPhoneNumberUtil
import kotlinx.cinterop.ExperimentalForeignApi
// iosMain source set
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSLocale
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterStyle
import platform.Foundation.currentLocale

actual fun Double.asCurrencyWith2Decimals(): String {
    val numberFormatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        maximumFractionDigits = 2u
        minimumFractionDigits = 2u
        locale = NSLocale.currentLocale
    }
    val number = NSNumber(this)
    return numberFormatter.stringFromNumber(number) ?: "$0.00"
}

@OptIn(ExperimentalForeignApi::class)
actual fun formatPhoneNumber(phoneNumber: String): String {
    val phoneUtil = NBPhoneNumberUtil()
    return try {
        val numberProto = phoneUtil.parse(phoneNumber, defaultRegion = "US", error = null)
        phoneUtil.format(numberProto, NBEPhoneNumberFormatINTERNATIONAL, error = null)
            ?: phoneNumber
    } catch (e: Exception) {
        phoneNumber
    }
}
