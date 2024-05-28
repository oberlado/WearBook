package com.example.wearbook.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MyAdapter(private var myDataset: Array<File>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return MyViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textView.text = myDataset[position].name
        holder.itemView.setOnClickListener {
            val file = myDataset[position]
            if (file.isDirectory) {
                (holder.itemView.context as FolderActivity).navigateToDirectory(file)
            } else {
                // Handle file click
            }
        }
    }

    override fun getItemCount() = myDataset.size

    fun updateFiles(files: Array<File>) {
        myDataset = files
        notifyDataSetChanged()
    }
}