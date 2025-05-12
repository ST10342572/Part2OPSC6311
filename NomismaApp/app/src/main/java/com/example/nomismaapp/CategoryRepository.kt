package com.example.nomismaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query

    @Dao
    interface CategoryDao {
        @Query("SELECT * FROM categories WHERE user_id = :userId")
        fun getAllCategoriesLive(userId: Long): LiveData<List<Category>>

        @Insert
        suspend fun insertCategory(category: Category): Long

        @Update
        suspend fun updateCategory(category: Category)

        @Delete
        suspend fun deleteCategory(category: Category)
    }


