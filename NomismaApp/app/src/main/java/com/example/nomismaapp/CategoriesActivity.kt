package com.example.nomismaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nomismaapp.databinding.ActivityCategoriesBinding

 class CategoriesActivity : AppCompatActivity() {
            private lateinit var binding: ActivityCategoriesBinding
            private lateinit var viewModel: CategoryViewModel
            private lateinit var adapter: CategoriesAdapter

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = DataBindingUtil.setContentView(this, R.layout.activity_categories)

                // Initialize ViewModel
                viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

                // Initialize RecyclerView and Adapter
                setupRecyclerView()

                // Observe the LiveData
                viewModel.allCategories.observe(this, { categories ->
                    // Update the UI with the new list of categories
                    adapter.updateCategories(categories)
                })

                binding.addBtn.setOnClickListener {
                    // Start NewCategoryActivity to add a new category
                    val intent = Intent(this, NewCategoryActivity::class.java)
                    startActivityForResult(intent, ADD_CATEGORY_REQUEST)
                }
            }

            private fun setupRecyclerView() {
                adapter = CategoriesAdapter(emptyList(),
                    onCategoryClicked = { category ->
                        // Handle category click
                        Toast.makeText(this, "Clicked category: $category", Toast.LENGTH_SHORT).show()
                    },
                    onDeleteClicked = { category ->
                        // Handle delete
                        viewModel.delete(category) // Use ViewModel to delete
                    })
                binding.categoriesRecyclerView.layoutManager = GridLayoutManager(this, 3) // 3 columns
                binding.categoriesRecyclerView.adapter = adapter
            }

            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                if (requestCode == ADD_CATEGORY_REQUEST && resultCode == RESULT_OK) {
                    // Reload categories after adding a new one
                    viewModel.allCategories.observe(this, { categories ->
                        adapter.updateCategories(categories)
                    })
                }
            }

            companion object {
                private const val ADD_CATEGORY_REQUEST = 1 // Request code for adding a category
            }
        }
