package com.example.nomismaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ExpenseEntry : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseEntryBinding
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense_entry)
        databaseHelper = DatabaseHelper(this)
        // Setting up listeners for the buttons
        binding.cancelButton.setOnClickListener { finish() }
        binding.saveButton.setOnClickListener {
            val expenseName = binding.expenseNameEditText.text.toString()
            val expenseAmount = binding.expenseAmountEditText.text.toString().toDoubleOrNull()
            if (expenseName.isNotEmpty() && expenseAmount != null) {
                saveExpense(expenseName, expenseAmount)
            } else {
                Toast.makeText(this, "Please enter valid expense details", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveExpense(name: String, amount: Double) {
        val insertRowId = databaseHelper.insertExpense(name, amount)
        if (insertRowId != -1L) {
            Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to save expense", Toast.LENGTH_SHORT).show()
        }
    }
}
