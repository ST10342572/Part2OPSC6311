package com.example.nomismaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryListAdapter(
    private var categories: List<String>,   // Or data model if you have one
    private val onCategoryClicked: (String) -> Unit,
    private val onDeleteClicked: (String) -> Unit
) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {
    fun updateCategories(newCategories: List<String>) {
        categories = newCategories
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onCategoryClicked, onDeleteClicked)
    }
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
    override fun getItemCount(): Int = categories.size
    class CategoryViewHolder(
        itemView: View,
        private val onCategoryClicked: (String) -> Unit,
        private val onDeleteClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val btnCategory: ImageButton = itemView.findViewById(R.id.btnCategory)
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        fun bind(category: String) {
            tvCategoryName.text = category
            btnCategory.setOnClickListener {
                onCategoryClicked(category)
            }
            // Optionally add a delete button and its handler
            itemView.setOnLongClickListener {
                onDeleteClicked(category)
                true // Return true to indicate the long click was handled
            }
        }
    }

}