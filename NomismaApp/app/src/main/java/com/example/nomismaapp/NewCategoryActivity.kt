package com.example.nomismaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class NewCategoryActivity : AppCompatActivity() {
   private lateinit var binding: ActivityNewCategoryBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_category)
        databaseHelper = DatabaseHelper(this)

        binding.doneButton.setOnClickListener {
            val categoryName = binding.categoryNameEditText.text.toString()
            if (categoryName.isNotEmpty()) {
                saveCategory(categoryName)
            } else {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveCategory(name: String) {
        val insertRowId = databaseHelper.insertCategory(name)
        if (insertRowId != -1L) {
            Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to save category", Toast.LENGTH_SHORT).show()
        }
    }
}
