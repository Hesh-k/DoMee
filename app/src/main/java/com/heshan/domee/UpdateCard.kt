package com.heshan.domee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.heshan.domee.databinding.ActivityUpdateCardBinding // Import your ViewBinding class
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateCard : AppCompatActivity() {
    private lateinit var database: myDatabase
    private lateinit var binding: ActivityUpdateCardBinding // Declare ViewBinding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater) // Initialize ViewBinding
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, myDatabase::class.java, "DoMee"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            lifecycleScope.launch {
                val allData = database.dao().getTasks()
                val task = allData[pos] as Entity
                binding.createTitle.setText(task.title)
                binding.createPriority.setText(task.priority)

                binding.deleteButton.setOnClickListener {
                    GlobalScope.launch {
                        database.dao().deleteTask(task.id)
                    }
                    myIntent()
                }

                binding.updateButton.setOnClickListener {
                    val updatedTask = Entity(
                        id = task.id,
                        title = binding.createTitle.text.toString(),
                        priority = binding.createPriority.text.toString()
                    )
                    GlobalScope.launch {
                        database.dao().updateTask(updatedTask)
                    }
                    myIntent()
                }
            }
        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}