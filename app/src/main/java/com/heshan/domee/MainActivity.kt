package com.heshan.domee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.heshan.domee.databinding.ActivityMainBinding // Import your ViewBinding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.util.Log // Import Log class
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {
    private lateinit var database: myDatabase // Database instance
    private lateinit var binding: ActivityMainBinding // Declare ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize ViewBinding
        setContentView(binding.root)

        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            myDatabase::class.java,
            "DoMee"
        ).build()

        // Set an OnClickListener for the add button
        binding.add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        // Set an OnClickListener for the deleteAll button
        binding.deleteAll.setOnClickListener {
            GlobalScope.launch {
                database.dao().deleteAll() // Delete all data from the database
            }
            setRecycler() // Update the RecyclerView
        }

        setRecycler() // Set up the RecyclerView
    }

    // Function to set up the RecyclerView
    private fun setRecycler() {
        lifecycleScope.launch {
            val allData: List<Entity> = database.dao().getTasks() // Get all tasks from the database
            Log.d("MainActivity", "Data retrieved from database at ${System.currentTimeMillis()}: $allData")

            val adapter = Adapter(allData) // Create an instance of the Adapter
            binding.recyclerView.adapter = adapter // Set the adapter for the RecyclerView
            binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext) // Set the layout manager
        }
    }

    // Called when the activity resumes
    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")
        setRecycler() // Update the RecyclerView
    }
}