package com.example.todoapp

import TodoItem
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.databinding.ActivityEditTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class EditTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTodoBinding
    private var todoItem: TodoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoItem = intent.getParcelableExtra("todoItem")

        if (todoItem != null) {
            binding.etTaskDescription.setText(todoItem?.text)
            binding.tvDeadline.text = todoItem?.deadline?.let { formatDate(it) } ?: "Дедлайн не выбран"
            binding.switchCompleted.isChecked = todoItem?.isCompleted ?: false
            binding.btnDelete.visibility = View.VISIBLE
        }

        setupPrioritySpinner()

        binding.btnSelectDeadline.setOnClickListener {
            showDatePicker()
        }

        binding.btnSave.setOnClickListener {
            saveTask()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            deleteTask()
        }
    }

    private fun setupPrioritySpinner() {
        val priorities = Importance.values().map { it.toString() }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter

        val priorityIndex = when (todoItem?.importance) {
            Importance.LOW -> 0
            Importance.NORMAL -> 1
            Importance.HIGH -> 2
            else -> 1
        }
        binding.spinnerPriority.setSelection(priorityIndex)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, day)
                }.time
                binding.tvDeadline.text = formatDate(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun saveTask() {
        val taskDescription = binding.etTaskDescription.text.toString()
        val priority = Importance.fromString(binding.spinnerPriority.selectedItem.toString())
        val deadline = if (binding.tvDeadline.text == "Дедлайн не выбран") null else parseDate(binding.tvDeadline.text.toString())
        val isCompleted = binding.switchCompleted.isChecked

        if (taskDescription.isEmpty()) {
            Toast.makeText(this, "Введите описание задачи", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedItem = todoItem?.copy(
            text = taskDescription,
            importance = priority,
            deadline = deadline,
            isCompleted = isCompleted,
            updatedAt = Date()
        ) ?: TodoItem(
            id = UUID.randomUUID().toString(),
            text = taskDescription,
            importance = priority,
            deadline = deadline,
            isCompleted = isCompleted,
            createdAt = Date(),
            updatedAt = null
        )

        val resultIntent = Intent().apply {
            putExtra("todoItem", updatedItem)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun deleteTask() {
        val resultIntent = Intent().apply {
            putExtra("todoItem", todoItem)
            putExtra("isDelete", true)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
    }

    private fun parseDate(dateString: String): Date? {
        return try {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(dateString)
        } catch (e: Exception) {
            null
        }
    }
}