package com.codefylabs.www.spider.core.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initializeLogger(){
    Napier.base(DebugAntilog())
}