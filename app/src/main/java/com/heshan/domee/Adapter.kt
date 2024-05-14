package com.heshan.domee

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.heshan.domee.databinding.ViewBinding // Import your ViewBinding class
import java.util.*

// Adapter class for RecyclerView
class Adapter(private var data: List<Entity>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    // ViewHolder class to hold the view references
    inner class ViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        // References to views in the ViewBinding
        val title = binding.title
        val priority = binding.priority
        val layout = binding.mylayout
    }

    // Create a new ViewHolder and initialize the ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = data[position]

        // Set the background color based on the priority
        when (currentItem.priority.toLowerCase(Locale.getDefault())) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#00917C"))
        }

        // Set the text for title and priority
        holder.title.text = currentItem.title
        holder.priority.text = currentItem.priority

        // Set an OnClickListener for the item view
        holder.itemView.setOnClickListener {
            // Create an Intent to navigate to UpdateCard activity
            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            // Pass the position as an extra
            intent.putExtra("id", position)
            // Start the activity
            holder.itemView.context.startActivity(intent)
        }
    }

    // Return the number of items in the data list
    override fun getItemCount() = data.size
}