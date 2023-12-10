package com.hujiayucc.rpointer.ui

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private lateinit var imageModelArrayList: ArrayList<ImageModel>
    private var recyclerItem = hashMapOf<String, Int>()

    init {
        recyclerItem["默认"] = R.drawable.pointer_arrow
        recyclerItem["默认2"] = R.drawable.ic_launcher_background
        recyclerItem["默认3"] = R.drawable.ic_success
        recyclerItem["默认4"] = R.drawable.ic_launcher_foreground
        recyclerItem["默认5"] = R.drawable.ic_warn
        recyclerItem["默认6"] = R.drawable.pointer_arrow
        recyclerItem["默认7"] = R.drawable.ic_launcher_background
        recyclerItem["默认8"] = R.drawable.ic_success
        recyclerItem["默认9"] = R.drawable.ic_launcher_foreground
        recyclerItem["默认10"] = R.drawable.ic_warn
    }

    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val buildTime: String = format.format(Date(YukiHookAPI.Status.compiledTimestamp))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        recyclerView = binding.recycler
        imageModelArrayList = populateList()

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

        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        adapter = CustomAdapter(this, imageModelArrayList)
        recyclerView.adapter = adapter
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

    private fun populateList(): ArrayList<ImageModel> {
        val list = ArrayList<ImageModel>()
        for (i in recyclerItem) {
            val imageModel = ImageModel()
            imageModel.name = i.key
            imageModel.imageUrl = i.value
            list.add(imageModel)
        }
        return list
    }
}