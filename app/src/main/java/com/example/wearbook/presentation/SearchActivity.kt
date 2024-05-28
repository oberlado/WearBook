package com.example.wearbook.presentation

import android.R
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import java.io.File
import java.lang.Long
import java.util.Collections
import kotlin.Comparator
import kotlin.Int
import kotlin.String

class SearchActivity : AppCompatActivity() {
    private var wearableRecyclerView: WearableRecyclerView? = null
    private val fileList: MutableList<File> = ArrayList()
    private val fileFormats: List<String> =
        mutableListOf(".txt", ".fb2") // добавьте сюда другие форматы

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wearableRecyclerView = findViewById<WearableRecyclerView>(R.id.searchRecyclerView)
        wearableRecyclerView.setHasFixedSize(true)
        wearableRecyclerView.setEdgeItemsCenteringEnabled(true)
        wearableRecyclerView.setLayoutManager(WearableLinearLayoutManager(this))

        searchFiles(Environment.getExternalStorageDirectory())

        Collections.sort(fileList, object : Comparator<File?> {
            override fun compare(f1: File, f2: File): Int {
                return Long.compare(f2.lastModified(), f1.lastModified())
            }
        })

        val fileNames: MutableList<String> = ArrayList()
        for (file in fileList) {
            fileNames.add(file.name)
        }

        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, fileNames)
        wearableRecyclerView.setAdapter(adapter)
    }

    private fun searchFiles(dir: File) {
        val files = dir.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    searchFiles(file)
                } else {
                    val fileName = file.name
                    val index = fileName.lastIndexOf(".")
                    if (index > 0) {
                        val extension = fileName.substring(index)
                        if (fileFormats.contains(extension)) {
                            fileList.add(file)
                        }
                    }
                }
            }
        }
    }
}

