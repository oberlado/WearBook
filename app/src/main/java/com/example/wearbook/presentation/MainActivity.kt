package com.example.wearbook.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.wearbook.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val recyclerView = findViewById<WearableRecyclerView>(R.id.main_recycler_view).apply {
            layoutManager = WearableLinearLayoutManager(this@MainActivity)
            adapter = ButtonAdapter(
                this@MainActivity,
                buttonNames = listOf("Search","Open","New", "Option"),
                buttonImages = listOf(R.drawable.splash_icon, R.drawable.splash_icon, R.drawable.splash_icon, R.drawable.splash_icon)
            )
        }
    }
}

class ButtonAdapter(private val context: AppCompatActivity, private val buttonNames: List<String>, private val buttonImages: List<Int>) : RecyclerView.Adapter<ButtonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.buttonName.text = buttonNames[position]
        holder.buttonImage.setImageResource(buttonImages[position])
        holder.itemView.setOnClickListener {
            when (position) {
                0 -> context.startActivity(Intent(context, OpenActivity::class.java))
                1 -> context.startActivity(Intent(context, OpenActivity::class.java))
                2 -> context.startActivity(Intent(context, FolderActivity::class.java))
                3 -> context.startActivity(Intent(context, OptionActivity::class.java))
            }
        }
    }

    override fun getItemCount() = buttonNames.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val buttonName: TextView = view.findViewById(R.id.button_name)
        val buttonImage: ImageView = view.findViewById(R.id.button_image)
    }
}



