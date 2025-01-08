package com.example.todoapp

enum class Importance {
    LOW,
    NORMAL,
    HIGH;

    override fun toString(): String {
        return when (this) {
            LOW -> "Низкая"
            NORMAL -> "Обычная"
            HIGH -> "Срочная"
        }
    }

    companion object {
        fun fromString(value: String): Importance {
            return when (value) {
                "Низкая" -> LOW
                "Обычная" -> NORMAL
                "Срочная" -> HIGH
                else -> NORMAL
            }
        }
    }
}