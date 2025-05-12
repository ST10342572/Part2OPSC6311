package com.example.nomismaapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "User Database.db"
        private const val DATABASE_VERSION = 1

        // User table
        private const val TABLE_USERS = "users"
        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        // Expense table
        private const val TABLE_EXPENSES = "expenses"
        private const val COLUMN_EXPENSE_ID = "id"
        private const val COLUMN_EXPENSE_NAME = "name"
        private const val COLUMN_EXPENSE_AMOUNT = "amount"

        // Category table
        private const val TABLE_CATEGORIES = "categories"
        private const val COLUMN_CATEGORY_ID = "id"
        private const val COLUMN_CATEGORY_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Users table
        val createUsersTable = ("CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(createUsersTable)

        // Create Expenses table
        val createExpensesTable = ("CREATE TABLE $TABLE_EXPENSES (" +
                "$COLUMN_EXPENSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_EXPENSE_NAME TEXT," +
                "$COLUMN_EXPENSE_AMOUNT REAL)")
        db?.execSQL(createExpensesTable)

        // Create Categories table
        val createCategoriesTable = ("CREATE TABLE $TABLE_CATEGORIES (" +
                "$COLUMN_CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_CATEGORY_NAME TEXT)")
        db?.execSQL(createCategoriesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropUsersTable = "DROP TABLE IF EXISTS $TABLE_USERS"
        val dropExpensesTable = "DROP TABLE IF EXISTS $TABLE_EXPENSES"
        val dropCategoriesTable = "DROP TABLE IF EXISTS $TABLE_CATEGORIES"
        db?.execSQL(dropUsersTable)
        db?.execSQL(dropExpensesTable)
        db?.execSQL(dropCategoriesTable)
        onCreate(db)
    }

    // Insert user
    fun insertUser (username: String, password: String): Long {
        val value = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_USERS, null, value)
    }

    // Insert expense
    fun insertExpense(name: String, amount: Double): Long {
        val value = ContentValues().apply {
            put(COLUMN_EXPENSE_NAME, name)
            put(COLUMN_EXPENSE_AMOUNT, amount)
        }
        val db = writableDatabase
        return db.insert(TABLE_EXPENSES, null, value)
    }

    // Insert category
    fun insertCategory(name: String): Long {
        val value = ContentValues().apply {
            put(COLUMN_CATEGORY_NAME, name)
        }
        val db = writableDatabase
        return db.insert(TABLE_CATEGORIES, null, value)
    }

    // Read user
    fun readDatabase(username: String, password: String): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null)

        val userExists = cursor.count > 0
        cursor.close()
        return userExists
    }

    // Get all categories
    fun getAllCategories(): List<String> {
        val categories = mutableListOf<String>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CATEGORIES", null)

        if (cursor.moveToFirst()) {
            do {
                val categoryName = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME))
                categories.add(categoryName)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return categories
    }
}

//class DatabaseHelper(private val context:Contex);//extend scriilihght open helper <!-- This is a comment -->
//SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)//scrrlight open helper constructor{
    // companion object{//used to define a singleton(make sure there is always obne object) object in a class//
