package com.hujiayucc.rpointer.hook

import android.graphics.Bitmap
import android.view.PointerIcon
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.method
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.type.android.ActivityClass
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.hujiayucc.rpointer.BuildConfig
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.appliication.Application


@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {

    private var bitmap: Bitmap? = null
    private var x = 0F
    private var y = 0F

    override fun onInit() = configs {
        isDebug = BuildConfig.DEBUG
        YLog.Configs.tag = Application.TAG
    }

    override fun onHook() = YukiHookAPI.encase {
        loadApp {
            ActivityClass.method {
                name = "onCreate"
                param(BundleClass)
            }.hook {
                after {
                    if (YukiHookAPI.Configs.isDebug) YLog.debug("Start hook packageName: $packageName")
                    bitmap = ResourcesCompat.getDrawable(moduleAppResources, R.drawable.pointer_arrow, moduleAppResources.newTheme())?.toBitmap()
                    bitmap?.let { bitmap ->
                        x = bitmap.width.toFloat() / 2F
                        y = bitmap.height.toFloat() / 2F
                    }
                    // activity.setIcon(fileDir ,bitmap)
                }
            }

            ViewClass.method {
                name = "onResolvePointerIcon"
            }.hook {
                YLog.Configs.isEnable = BuildConfig.DEBUG
                before {
                     bitmap?.let { result = PointerIcon.create(it,0f,0f) }
                }
            }
        }
    }

    /*
    private fun Activity.setIcon(fileDir: File, bitmap: Bitmap?) {
        if (bitmap == null) return
        val configFile = File(fileDir,"config.conf")
        val pointerIcon = PointerIcon.create(bitmap,0f,0f)
        window.decorView.setOnHoverListener { _, _ ->
            window.decorView.pointerIcon = pointerIcon
            true
        }
    }
     */
}