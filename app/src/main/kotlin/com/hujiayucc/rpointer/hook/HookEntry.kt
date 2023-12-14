package com.hujiayucc.rpointer.hook

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.hujiayucc.rpointer.BuildConfig
import com.hujiayucc.rpointer.application.Application
import com.hujiayucc.rpointer.hook.hooker.AppIcon
import com.hujiayucc.rpointer.ui.adapter.Type
import com.hujiayucc.rpointer.utils.Data.hookType

@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
        YLog.Configs.tag = Application.TAG
    }

    override fun onHook() = YukiHookAPI.encase {
        val type = prefs.get(hookType)
        loadApp(packageName) {
            when (type) {
                Type.App.name -> loadHooker(AppIcon)
                else -> {}
            }
        }
    }
}