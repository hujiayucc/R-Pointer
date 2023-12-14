package com.hujiayucc.rpointer.ui.adapter

// Import necessary modules
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.highcapable.yukihookapi.hook.factory.prefs
import com.highcapable.yukihookapi.hook.xposed.application.ModuleApplication.Companion.appContext
import com.hujiayucc.rpointer.R
import com.hujiayucc.rpointer.utils.Data.hookIcon
import com.hujiayucc.rpointer.utils.Data.hookType

class CustomAdapter(private val context: Context, private val imageModelArrayList: ArrayList<ImageModel<Any>>)
    : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.it)
        var imageView: ImageView = view.findViewById(R.id.iv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_custom_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = imageModelArrayList[position]
        val imageView = holder.imageView
        if (model.type == Type.App) {
            (model as ImageModel<Int>).image?.let {
                imageView.setImageResource(it)
                imageView.minimumHeight = 350
            }

            holder.itemView.setOnClickListener {
                appContext.prefs().edit {
                    put(hookType, model.type.name)
                    (model as ImageModel<Int>).image?.let {
                        put(hookIcon, it)
                    }
                }
            }
        }
        holder.textView.text = model.name
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }
}
