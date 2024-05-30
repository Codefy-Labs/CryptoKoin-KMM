package com.codefylabs.www.spider.android.core.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.openLink(url: String) = runCatching {
    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}.onFailure { it.printStackTrace() }

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}