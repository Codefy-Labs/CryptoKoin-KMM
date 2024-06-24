package com.codefylabs.www.canimmigrate.core.util

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun initializeLogger(){
    Napier.base(DebugAntilog())
}