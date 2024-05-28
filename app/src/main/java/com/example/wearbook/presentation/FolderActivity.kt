package com.example.wearbook.presentation

import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import androidx.wear.widget.WearableRecyclerView
import com.example.wearbook.R
import java.io.File
import java.util.Stack

class FolderActivity : AppCompatActivity() {
    private lateinit var recyclerView: WearableRecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var currentDirectory: File = Environment.getExternalStorageDirectory()
    private val directoryStack = Stack<File>()
    private val allowedExtensions = listOf("txt", "fb2", "epub", "docx")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewManager = WearableLinearLayoutManager(this)
        viewAdapter = MyAdapter(getFiles(currentDirectory))

        recyclerView = findViewById<WearableRecyclerView>(R.id.folder_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun getFiles(directory: File): Array<File> {
        return directory.listFiles { file ->
            file.isDirectory || allowedExtensions.any { file.name.endsWith(it) }
        } ?: arrayOf()
    }

    fun navigateToDirectory(directory: File) {
        directoryStack.push(currentDirectory)
        currentDirectory = directory
        (viewAdapter as MyAdapter).updateFiles(getFiles(directory))
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

    override fun onBackPressed() {
        if (directoryStack.isEmpty()) {
            super.onBackPressed()
        } else {
            currentDirectory = directoryStack.pop()
            (viewAdapter as MyAdapter).updateFiles(getFiles(currentDirectory))
        }
    }
}



