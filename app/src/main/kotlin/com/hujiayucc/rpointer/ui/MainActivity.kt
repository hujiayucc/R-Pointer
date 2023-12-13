package com.hujiayucc.rpointer.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.application.ModuleApplication.Companion.appContext
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.base.ModuleAppCompatActivity
import com.hujiayucc.rpointer.BuildConfig
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.databinding.ActivityMainBinding
import com.hujiayucc.rpointer.utils.Data.languageItem
import com.hujiayucc.rpointer.utils.Data.languages
import com.hujiayucc.rpointer.utils.Data.localeList
import com.hujiayucc.rpointer.utils.Data.prefsData
import com.hujiayucc.rpointer.utils.Data.recyclerState
import com.hujiayucc.rpointer.utils.Data.themeItems
import com.hujiayucc.rpointer.utils.Data.themeList
import com.hujiayucc.rpointer.utils.Data.themePref
import com.hujiayucc.rpointer.utils.Language
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : ModuleAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var imageModelArrayList: ArrayList<ImageModel<Any>>
    private var recyclerItem = hashMapOf<String, Int>()

    init {
        appContext.initLocalIcon()
    }

    private fun Context.initLocalIcon() {
        recyclerItem.clear()
        recyclerItem[getString(R.string.pointer_icon_hide)] = R.drawable.pointer_hide
        recyclerItem[getString(R.string.pointer_icon_default)] = R.drawable.pointer_arrow
    }

    private fun Context.setText() {
        if (YukiHookAPI.Status.isModuleActive) {
            binding.mainImgStatus.setImageResource(R.drawable.ic_success)
            binding.mainStatus.text = getString(R.string.is_active)
            binding.mainFramework.visibility = View.VISIBLE
            binding.mainFramework.text = getString(R.string.main_framework).format(YukiHookAPI.Status.Executor.name, "API ${YukiHookAPI.Status.Executor.apiLevel}")
        }

        binding.mainActiveStatus.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_header, theme)
        binding.mainVersion.text = getString(R.string.main_version).format(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        binding.mainDate.text = getString(R.string.buildTlite).format(buildTime)
    }

    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val buildTime: String = format.format(Date(YukiHookAPI.Status.compiledTimestamp))

    override fun onCreate(savedInstanceState: Bundle?) {
        val themeItem = prefsData.get(themePref)
        setTheme(themeList[themeItem])
        val language = prefsData.get(languages)
        if (language != 0) checkLanguage(Language.fromId(language),true)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        recyclerView = binding.recycler
        imageModelArrayList = populateList()

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setText()

        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        adapter = CustomAdapter(this, imageModelArrayList)
        recyclerView.adapter = adapter
        recyclerState?.let { recyclerView.layoutManager?.onRestoreInstanceState(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_minimize -> {
                super.finishAndRemoveTask()
                true
            }
            R.id.action_theme -> {
                val checkedItem = prefsData.get(themePref)
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.theme_color))
                    .setSingleChoiceItems(themeItems, checkedItem) { dialog, which ->
                        setTheme(themeList[which])
                        prefsData.edit {
                            put(themePref, which)
                            commit()
                        }
                        dialog.dismiss()
                        recreate()
                    }
                    .create().show()
                true
            }
            R.id.action_language -> {
                val checkedItem = prefsData.get(languages)

                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.language_setting))
                    .setSingleChoiceItems(languageItem, checkedItem) { dialog, which ->
                        checkLanguage(localeList[which],false)
                        prefsData.edit {
                            put(languages, which)
                            commit()
                        }
                        dialog.dismiss()
                        recreate()
                    }
                    .create().show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun finish() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        excludeFromRecent(true)
    }

    override fun onResume() {
        super.onResume()
        excludeFromRecent(false)
    }

    private fun excludeFromRecent(exclude: Boolean) {
        try {
            val manager: ActivityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
            for (appTask in manager.appTasks) {
                @Suppress("DEPRECATION")
                if (appTask.taskInfo.id == taskId) {
                    appTask.setExcludeFromRecents(exclude)
                }
            }
        } catch (e: Exception) {
            YLog.error(e = e)
        }
    }

    @Suppress("DEPRECATION")
    private fun checkLanguage(language: Locale, isInit: Boolean) {
        val configuration = resources.configuration
        configuration.setLocale(language)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        initLocalIcon()
        if (isInit.not()) {
            setText()
            recreate()
        }
    }

    override fun recreate() {
        recyclerState = recyclerView.layoutManager?.onSaveInstanceState()
        super.recreate()
    }

    private fun populateList(): ArrayList<ImageModel<Any>> {
        val list = ArrayList<ImageModel<Any>>()
        for (item in recyclerItem) {
            val imageModel = ImageModel<Any>()
            imageModel.name = item.key
            imageModel.image = item.value
            list.add(imageModel)
        }
        return list
    }
}