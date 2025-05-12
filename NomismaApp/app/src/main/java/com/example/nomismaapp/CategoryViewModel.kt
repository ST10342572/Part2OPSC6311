package com.example.nomismaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CategoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository
    val allCategories: LiveData<List<Category>>
    private val userId: Long
    init {
        val db = AppDatabase.getDatabase(application)
        repository = CategoryRepository(db.categoryDao())
        userId = getUser IdFromPreferences(application)
        allCategories = repository.getAllCategories(userId)
    }
    fun insert(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(category)
    }
    fun update(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(category)
    }
    fun delete(category: Category) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(category)
    }
    private fun getUser IdFromPreferences(context: Context): Long {
        val sharedPrefs = context.getSharedPreferences("budget_tracker_prefs", Context.MODE_PRIVATE)
        return sharedPrefs.getLong("user_id", -1L)
    }
}
