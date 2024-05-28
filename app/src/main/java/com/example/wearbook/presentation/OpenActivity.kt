package com.example.wearbook.presentation

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.wearbook.R
import java.io.File
import java.util.Stack

class OpenActivity : AppCompatActivity() {
    private lateinit var recyclerView: WearableRecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val openedFiles = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Загрузить историю открытых файлов
        loadOpenedFiles()

        viewManager = WearableLinearLayoutManager(this)
        viewAdapter = MyAdapter(openedFiles.toTypedArray())

        recyclerView = findViewById<WearableRecyclerView>(R.id.open_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        if (openedFiles.isEmpty()) {
            Toast.makeText(this, "Открытые файлы отсутствуют", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadOpenedFiles() {
        // Загрузить историю открытых файлов из SharedPreferences
        val sharedPreferences = getSharedPreferences("opened_files", Context.MODE_PRIVATE)
        val filesString = sharedPreferences.getString("files", "") ?: ""
        for (filePath in filesString.split(";")) {
            if (filePath.isNotEmpty()) {
                val file = File(filePath)
                if (file.isFile) { // Добавить файл только если это не каталог
                    openedFiles.add(file)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

