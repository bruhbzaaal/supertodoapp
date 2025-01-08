package com.example.todoapp

import TodoAdapter
import TodoItem
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    private val REQUEST_CODE_EDIT_TODO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = TodoItemsRepository()
        val factory = TodoViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)

        viewModel.todoItems.observe(this) { items ->
            todoAdapter.updateTodoItems(items)
        }

        todoAdapter = TodoAdapter(emptyList()) { todoItem ->
            openEditTodoActivity(todoItem)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.todoRv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = todoAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            openEditTodoActivity(null)
        }
    }

    private fun openEditTodoActivity(todoItem: TodoItem?) {
        val intent = Intent(this, EditTodoActivity::class.java).apply {
            putExtra("todoItem", todoItem)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT_TODO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_EDIT_TODO && resultCode == RESULT_OK) {
            val todoItem = data?.getParcelableExtra<TodoItem>("todoItem")
            if (todoItem != null) {
                if (data.getBooleanExtra("isDelete", false)) {
                    viewModel.removeTodoItem(todoItem)
                } else {
                    if (viewModel.todoItems.value?.any { it.id == todoItem.id } == true) {
                        viewModel.updateTodoItem(todoItem)
                    } else {
                        viewModel.addTodoItem(todoItem)
                    }
                }
            }
        }
    }
}