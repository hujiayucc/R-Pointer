package com.hujiayucc.rpointer.ui

// Import necessary modules
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hujiayucc.rpointer.R
import java.util.ArrayList

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
        if (model.type == Type.App)
            (model as ImageModel<Int>).image?.let {
                imageView.setImageResource(it)
                imageView.minimumHeight = 350
            }
        holder.textView.text = model.name
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }
}
