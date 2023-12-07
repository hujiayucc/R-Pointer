# R-Pointer

This module is for modifying the icon of the mouse pointer

English | [简体中文](https://github.com/hujiayucc/R-Pointer/blob/master/README-zh_CN.md)

## Features

- Provides a custom mouse pointer icon feature.
- Provides multiple preset mouse pointer icons for users to choose from. (Under development)

## How to Use

1. Install the LSPosed framework.
2. Download and install this module.
3. Enable this module in the LSPosed framework.
4. Check the scope in the LSPosed framework.
5. Restart the application.

## Developer

hujiayucc

## Development Frameworks

- [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI/)
- [LSPosed](https://github.com/LSPosed/LSPosed)
- [Xposed](https://api.xposed.info/reference/packages.html)

## Supported Environments

- [LSPosed](https://github.com/LSPosed/LSPosed)
- [LSPatch](https://github.com/LSPosed/LSPatch)
- For others, please test yourself

## Core Code
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

// It can also be done this way
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

## License

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