package com.hujiayucc.rpointer.ui

data class ImageModel<T> (
    var name: String = "",
    var type: Type = Type.App,
    var image: T? = null
)

enum class Type {
    App,
    Local,
    Internet
}