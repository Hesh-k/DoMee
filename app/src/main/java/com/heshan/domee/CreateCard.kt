package com.heshan.domee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import com.heshan.domee.databinding.ActivityCreateCardBinding // Import your ViewBinding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                GlobalScope.launch {
                    database.dao().insertTask(Entity(0, title, priority))
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Title and Priority cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
