# R-Pointer

此模块用于修改鼠标指针图标

[English](https://github.com/hujiayucc/R-Pointer/blob/master/README.md) | 简体中文

## 功能

- 提供自定义鼠标指针图标功能。
- 提供多个预设鼠标指针图标供用户选择。(待开发)

## 使用方法

1. 安装 LSPosed 框架。
2. 下载并安装本模块。
3. 在 LSPosed 框架中启用此模块。
4. 在 LSPosed 框架中勾选作用域。
5. 重启应用。

## 开发者

hujiayucc

## 开发框架

- [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI/)
- [LSPosed](https://github.com/LSPosed/LSPosed)
- [Xposed](https://api.xposed.info/reference/packages.html)

## 支持环境

- [LSPosed](https://github.com/LSPosed/LSPosed)
- [LSPatch](https://github.com/LSPosed/LSPatch)
- 其他请自行测试

## 核心代码
```kotlin
ActivityClass.method {
    name = "onCreate"
    param(BundleClass)
}.hook {
    after {
        if (YukiHookAPI.Configs.isDebug) YLog.debug("Start hook packageName: $packageName")
        bitmap = ResourcesCompat.getDrawable(moduleAppResources, R.drawable.pointer_arrow, moduleAppResources.newTheme())?.toBitmap()
        // activity.setIcon(fileDir ,bitmap)
    }
}

ViewClass.method {
    name = "onResolvePointerIcon"
}.hook {
    YLog.Configs.isEnable = false
    before {
        bitmap?.let { result = PointerIcon.create(it,0f,0f) }
    }
}

// You can also do it this way
private fun Activity.setIcon(fileDir: File, bitmap: Bitmap?) {
    if (bitmap == null) return
    val configFile = File(fileDir,"config.conf")
    val pointerIcon = PointerIcon.create(bitmap,0f,0f)
    window.decorView.setOnHoverListener { _, _ ->
        window.decorView.pointerIcon = pointerIcon
        true
    }
}
```

## 许可证

- [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Apache License Version 2.0

Copyright (C) 2023 hujiayucc

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```