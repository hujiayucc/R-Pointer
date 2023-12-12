package com.hujiayucc.rpointer.utils

import android.content.Context
import android.os.Parcelable
import com.highcapable.yukihookapi.hook.factory.prefs
import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData
import com.hujiayucc.rpointer.R

object Data {
    var recyclerState: Parcelable? = null
    val Context.prefsData get() = prefs("config")
    val themePref = PrefsData("themeItem", 0)
    val language = PrefsData("language", 0)
    val themeList = intArrayOf(
        R.style.Theme_RPointer_Default,
        R.style.Theme_RPointer_Pink,
        R.style.Theme_RPointer_Blue,
        R.style.Theme_RPointer_Yellow,
        R.style.Theme_RPointer_Green,
        R.style.Theme_RPointer_Red,
        R.style.Theme_RPointer_Orange
    )
    val Context.themeItems get() = arrayOf(
        getString(R.string.theme_default),
        getString(R.string.pink),
        getString(R.string.blue),
        getString(R.string.yellow),
        getString(R.string.green),
        getString(R.string.red),
        getString(R.string.orange)
    )
    val Context.languageItem get() = arrayOf(
        getString(R.string.language_auto),
        getString(R.string.language_english),
        getString(R.string.language_chinese)
    )
}