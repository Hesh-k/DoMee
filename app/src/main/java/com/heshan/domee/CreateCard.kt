package com.heshan.domee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import androidx.lifecycle.lifecycleScope
import com.heshan.domee.databinding.ActivityCreateCardBinding // Import ViewBinding class
import kotlinx.coroutines.launch
import android.util.Log // Import Log class
import kotlinx.coroutines.delay // Import delay function

class CreateCard : AppCompatActivity() {
    private lateinit var database: myDatabase // Database instance
    private lateinit var binding: ActivityCreateCardBinding // Declare ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater) // Initialize ViewBinding
        setContentView(binding.root)

        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            myDatabase::class.java,
            "DoMee"
        ).build()

        // Set an OnClickListener for the saveButton
        binding.saveButton.setOnClickListener {
            val title = binding.createTitle.text.toString().trim() // Get the title from the EditText
            val priority = binding.createPriority.selectedItem.toString() // Get the selected priority

            // Check if title and priority are not empty
            if (title.isNotEmpty() && priority.isNotEmpty()) {
                lifecycleScope.launch {
                    // Insert the task into the database
                    database.dao().insertTask(Entity(0, title, priority))
                    Log.d("CreateCard", "Task inserted into database at ${System.currentTimeMillis()}: Entity(0, $title, $priority)")

                    // Retrieve all data from the database
                    val allData = database.dao().getTasks()
                    Log.d("MainActivity", "Data retrieved from database at ${System.currentTimeMillis()}: $allData ")

                    delay(500) // Wait for 500 milliseconds

                    // Navigate to the MainActivity
                    val intent = Intent(this@CreateCard, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                // Show a Toast message if title or priority is empty
                Toast.makeText(this, "Title and Priority cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}