package com.hujiayucc.rpointer.hook.base

import android.app.Activity
import android.graphics.Bitmap
import android.view.PointerIcon

object Base {
    var bitmap: Bitmap? = null
    var x = 0F
    var y = 0F
    fun Activity.setIcon(bitmap: Bitmap?) {
        if (bitmap == null) return
        val pointerIcon = PointerIcon.create(bitmap, x, y)
        window.decorView.setOnHoverListener { _, _ ->
            window.decorView.pointerIcon = pointerIcon
            true
        }
    }
}