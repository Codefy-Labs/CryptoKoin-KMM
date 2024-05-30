package com.codefylabs.www.cryptokoin

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion


}

actual fun getPlatform(): Platform = IOSPlatform()