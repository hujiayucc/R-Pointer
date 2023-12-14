package com.hujiayucc.rpointer.hook.hooker

import android.app.Activity
import android.view.PointerIcon
import android.widget.Toast
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
import com.hujiayucc.rpointer.utils.Data

object AppIcon: YukiBaseHooker() {
    override fun onHook() {
        ActivityClass.method {
            name = "onCreate"
            param(BundleClass)
        }.hook {
            after {
                if (YukiHookAPI.Configs.isDebug) YLog.debug("Start hook packageName: $packageName")
                val type = prefs.get(Data.hookType)
                if (type == Type.App.name) {
                    bitmap = ResourcesCompat.getDrawable(
                        moduleAppResources,
                        prefs.getInt(Data.hookIcon.key, R.drawable.pointer_arrow),
                        moduleAppResources.newTheme()
                    )?.toBitmap()
                    bitmap?.let { bitmap ->
                        x = bitmap.width.toFloat() / 2F
                        y = bitmap.height.toFloat() / 2F
                    }
                }
                val activity = this.instance<Activity>()
                activity.setIcon(bitmap)
                Toast.makeText(activity, "${prefs.get(Data.hookIcon)}", Toast.LENGTH_SHORT).show()
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