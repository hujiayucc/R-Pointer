package com.hujiayucc.rpointer.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcelable
import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.ui.adapter.Type
import java.util.*

object Data {
    var recyclerState: Parcelable? = null
    val themePref = PrefsData("themeItem", 0)
    val languages = PrefsData("language", 0)
    val hookType = PrefsData("hookType", Type.App.name)
    val hookIcon = PrefsData<Any>("hookIcon", R.drawable.pointer_arrow)
    val themeList = intArrayOf(
        R.style.Theme_RPointer_Default,
        R.style.Theme_RPointer_Pink,
        R.style.Theme_RPointer_Blue,
        R.style.Theme_RPointer_Yellow,
        R.style.Theme_RPointer_Green,
        R.style.Theme_RPointer_Red,
        R.style.Theme_RPointer_Orange
    )
    var recyclerItem = hashMapOf<String, Int>()
    val Context.themeItems get() = arrayOf(
        getString(R.string.theme_default),
        getString(R.string.pink),
        getString(R.string.blue),
        getString(R.string.yellow),
        getString(R.string.green),
        getString(R.string.red),
        getString(R.string.orange)
    )
    @SuppressLint("ConstantLocale")
    val localeList = arrayOf(
        Locale.getDefault(),
        Locale.ENGLISH,
        Locale.CHINESE
    )
    val Context.languageItem get() = arrayOf(
        getString(R.string.language_auto),
        getString(R.string.language_english),
        getString(R.string.language_chinese)
    )
}