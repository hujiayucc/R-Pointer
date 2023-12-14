package com.hujiayucc.rpointer.hook.hooker

import android.app.Activity
import android.view.PointerIcon
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.hujiayucc.rpointer.BuildConfig
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.hook.base.Base.bitmap
import com.hujiayucc.rpointer.hook.base.Base.setIcon
import com.hujiayucc.rpointer.hook.base.Base.x
import com.hujiayucc.rpointer.hook.base.Base.y
import com.hujiayucc.rpointer.ui.adapter.Type
import com.hujiayucc.rpointer.utils.Data.hookIcon
import com.hujiayucc.rpointer.utils.Data.hookType
import com.hujiayucc.rpointer.utils.Data.themeList
import com.hujiayucc.rpointer.utils.Data.themePref

object AppIcon: YukiBaseHooker() {
    override fun onHook() {
        ActivityClass.method {
            name = "onCreate"
            param(BundleClass)
        }.hook {
            after {
                if (YukiHookAPI.Configs.isDebug) YLog.debug("Start hook packageName: $packageName")
                val type = prefs.get(hookType)
                val resources = moduleAppResources
                val themeItem = prefs.get(themePref)
                val theme = resources.newTheme()
                theme.applyStyle(themeList[themeItem],true)
                if (type == Type.App.name) {
                    bitmap = ResourcesCompat.getDrawable(
                        resources,
                        prefs.getInt(hookIcon.key, R.drawable.pointer_arrow),
                        theme
                    )?.toBitmap()
                }
                val activity = this.instance<Activity>()
                activity.setIcon(bitmap)
            }
        }

        ViewClass.method {
            name = "onResolvePointerIcon"
        }.hook {
            YLog.Configs.isEnable = BuildConfig.DEBUG
            before {
                bitmap?.let { result = PointerIcon.create(it, x, y) }
            }
        }
    }
}