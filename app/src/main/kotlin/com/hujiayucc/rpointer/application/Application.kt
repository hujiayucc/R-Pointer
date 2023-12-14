package com.hujiayucc.rpointer.application

import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.application.ModuleApplication
import com.hujiayucc.rpointer.BuildConfig

class Application : ModuleApplication() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            YLog.Configs.tag = TAG
            YLog.debug("Open R-Pointer Applicaion")
        }
    }

    companion object {
        val TAG: String = "R-Pointer"
    }
}