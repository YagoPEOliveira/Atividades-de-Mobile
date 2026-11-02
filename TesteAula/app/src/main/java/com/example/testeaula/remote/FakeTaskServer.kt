package com.example.testeaula.remote

import kotlinx.coroutines.delay

data class RemoteTask(
    val id: Long,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)

class FakeTaskServer {

    private val tasks = mutableListOf<RemoteTask>()

    private var nextId = 1L

    var isOnline = true

    private suspend fun checkConnection() {
        delay(1000)

        if (!isOnline) {
            throw Exception("Servidor indisponível")
        }
    }

    suspend fun getTasks(): List<RemoteTask> {
        checkConnection()
        return tasks.toList()
    }

    suspend fun createTask(
        title: String,
        description: String,
        isCompleted: Boolean
    ): RemoteTask {
        checkConnection()

        val task = RemoteTask(
            id = nextId++,
            title = title,
            description = description,
            isCompleted = isCompleted
        )

        tasks.add(task)

        return task
    }

    suspend fun updateTask(task: RemoteTask) {
        checkConnection()

        val index = tasks.indexOfFirst { it.id == task.id }

        if (index != -1) {
            tasks[index] = task
        }
    }

    suspend fun deleteTask(taskId: Long) {
        checkConnection()

        tasks.removeAll { it.id == taskId }
    }
}