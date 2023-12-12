package com.hujiayucc.rpointer.utils

import java.util.*

enum class Language(val id: Int, val locale: Locale) {
    DEFAULT(0, Locale.getDefault()),
    ENGLISH(1, Locale.ENGLISH),
    CHINESE(2, Locale.CHINESE);

    companion object {
        fun fromId(id: Int): Locale {
            return entries.find { it.id == id }?.locale ?: Locale.getDefault()
        }
    }
}