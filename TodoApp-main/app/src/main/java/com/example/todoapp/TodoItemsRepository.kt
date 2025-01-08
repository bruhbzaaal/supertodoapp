package com.example.todoapp

import TodoItem
import androidx.lifecycle.MutableLiveData
import java.util.*

class TodoItemsRepository {

    val todoItemsLiveData = MutableLiveData<List<TodoItem>>()

    init {
        val todoItems = mutableListOf<TodoItem>()

        // Initialize with diverse TodoItems
        val item1 = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "Complete the assignment",
            importance = Importance.HIGH,
            deadline = null,
            isCompleted = true,
            createdAt = Date(),
            updatedAt = null
        )
        todoItems.add(item1)

        val item2 = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "Prepare for the presentation",
            importance = Importance.NORMAL,
            deadline = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 7)
            }.time,
            isCompleted = false,
            createdAt = Date(),
            updatedAt = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -1)
            }.time
        )
        todoItems.add(item2)

        val item3 = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "Write a detailed report on the project progress. " +
                    "Ensure it covers all aspects including introduction, methodology, results, and conclusion. " +
                    "Include charts and graphs to support your findings.",
            importance = Importance.LOW,
            deadline = null,
            isCompleted = false,
            createdAt = Date(),
            updatedAt = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, -2)
            }.time
        )
        todoItems.add(item3)

        todoItemsLiveData.value = todoItems
    }

    fun addTodoItem(item: TodoItem) {
        val newList = todoItemsLiveData.value?.toMutableList() ?: mutableListOf()
        newList.add(item)
        todoItemsLiveData.value = newList
    }

    fun updateTodoItem(item: TodoItem) {
        val newList = todoItemsLiveData.value?.toMutableList() ?: mutableListOf()
        val index = newList.indexOfFirst { it.id == item.id }
        if (index != -1) {
            newList[index] = item
            todoItemsLiveData.value = newList
        }
    }

    fun removeTodoItem(item: TodoItem) {
        val newList = todoItemsLiveData.value?.toMutableList() ?: mutableListOf()
        newList.remove(item)
        todoItemsLiveData.value = newList
    }

    fun getTodoItemById(id: String): TodoItem? {
        return todoItemsLiveData.value?.find { it.id == id }
    }
}