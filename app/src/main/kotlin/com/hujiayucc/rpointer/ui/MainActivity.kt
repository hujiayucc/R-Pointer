package com.hujiayucc.rpointer.ui

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.base.ModuleAppCompatActivity
import com.hujiayucc.rpointer.BuildConfig
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ModuleAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val buildTime: String = format.format(Date(YukiHookAPI.Status.compiledTimestamp))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (YukiHookAPI.Status.isModuleActive) {
            binding.mainImgStatus.setImageResource(R.drawable.ic_success)
            binding.mainStatus.text = getString(R.string.is_active)
            binding.mainFramework.visibility = View.VISIBLE
            binding.mainFramework.text = getString(R.string.main_framework).format(YukiHookAPI.Status.Executor.name, "API ${YukiHookAPI.Status.Executor.apiLevel}")
        }

        binding.mainActiveStatus.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_header, theme)
        binding.mainVersion.text = getString(R.string.main_version).format(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        binding.mainDate.text = "Build Time: ${buildTime}"
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
                if (appTask.taskInfo.id == taskId) {
                    appTask.setExcludeFromRecents(exclude)
                }
            }
        } catch (e: Exception) {
            YLog.error(e = e)
        }
    }
}