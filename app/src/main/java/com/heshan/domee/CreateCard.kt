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
    private lateinit var database: myDatabase
    private lateinit var binding: ActivityCreateCardBinding // Declare ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater) // Initialize ViewBinding
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "DoMee"
        ).build()

        binding.saveButton.setOnClickListener {
            val title = binding.createTitle.text.toString().trim()
            val priority = binding.createPriority.text.toString().trim()

            if (title.isNotEmpty() && priority.isNotEmpty()) {
                lifecycleScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))
                    Log.d("CreateCard", "Task inserted into database at ${System.currentTimeMillis()}: Entity(0, $title, $priority)")

                    val allData = database.dao().getTasks()
                    Log.d("MainActivity", "Data retrieved from database at ${System.currentTimeMillis()}: $allData ")
                    delay(500) // Wait for 500 milliseconds
                    val intent = Intent(this@CreateCard, MainActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Title and Priority cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}