package com.example.todoapp

import TodoItem
import androidx.lifecycle.ViewModel

class TodoViewModel(private val repository: TodoItemsRepository) : ViewModel() {

    val todoItems = repository.todoItemsLiveData

    fun addTodoItem(item: TodoItem) {
        repository.addTodoItem(item)
    }

    fun updateTodoItem(item: TodoItem) {
        repository.updateTodoItem(item)
    }

    fun removeTodoItem(item: TodoItem) {
        repository.removeTodoItem(item)
    }
}