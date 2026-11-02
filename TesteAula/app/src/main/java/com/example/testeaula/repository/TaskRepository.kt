package com.example.testeaula.repository

import com.example.testeaula.data.TaskDao
import com.example.testeaula.data.TaskEntity
import com.example.testeaula.remote.FakeTaskServer
import com.example.testeaula.remote.RemoteTask
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val fakeServer: FakeTaskServer
) {

    // Observa as tarefas armazenadas localmente.
    val allTasks: Flow<List<TaskEntity>> =
        taskDao.getAllTasks()

    // Cria uma tarefa no banco local.
    suspend fun createTask(
        title: String,
        description: String
    ) {
        val task = TaskEntity(
            title = title,
            description = description
        )

        taskDao.insertTask(task)
    }

    // Altera o estado de conclusão de uma tarefa.
    suspend fun toggleTask(task: TaskEntity) {
        taskDao.updateTask(
            task.copy(
                isCompleted = !task.isCompleted,
                syncStatus = TaskEntity.SYNC_PENDING
            )
        )
    }

    // Exclui uma tarefa localmente.
    suspend fun deleteTask(task: TaskEntity) {
        if (task.remoteId == null) {
            // A tarefa ainda não existe no servidor.
            taskDao.deleteTask(task)
        } else {
            // Precisamos solicitar a exclusão no servidor depois.
            taskDao.updateTask(
                task.copy(
                    syncStatus = TaskEntity.SYNC_DELETE_PENDING
                )
            )
        }
    }

    // Sincroniza as alterações locais com o servidor.
    suspend fun synchronize() {

        val pendingTasks = taskDao.getTasksPendingSync()

        for (task in pendingTasks) {

            when (task.syncStatus) {

                TaskEntity.SYNC_PENDING -> {

                    if (task.remoteId == null) {

                        val remoteTask = fakeServer.createTask(
                            title = task.title,
                            description = task.description,
                            isCompleted = task.isCompleted
                        )

                        taskDao.updateTask(
                            task.copy(
                                remoteId = remoteTask.id,
                                syncStatus = TaskEntity.SYNCED
                            )
                        )

                    } else {

                        fakeServer.updateTask(
                            RemoteTask(
                                id = task.remoteId,
                                title = task.title,
                                description = task.description,
                                isCompleted = task.isCompleted
                            )
                        )

                        taskDao.updateTask(
                            task.copy(
                                syncStatus = TaskEntity.SYNCED
                            )
                        )
                    }
                }

                TaskEntity.SYNC_DELETE_PENDING -> {

                    fakeServer.deleteTask(task.remoteId!!)

                    taskDao.deleteTask(task)
                }
            }
        }
        // 2. Baixa as tarefas existentes no servidor.
        val remoteTasks = fakeServer.getTasks()

        // 3. Atualiza ou cadastra as tarefas no banco local.
        for (remoteTask in remoteTasks) {

            val localTask = taskDao.getTaskByRemoteId(remoteTask.id)

            if (localTask == null) {

                // A tarefa existe no servidor, mas ainda não existe localmente.
                taskDao.insertTask(
                    TaskEntity(
                        title = remoteTask.title,
                        description = remoteTask.description,
                        isCompleted = remoteTask.isCompleted,
                        remoteId = remoteTask.id,
                        syncStatus = TaskEntity.SYNCED
                    )
                )

            } else if (localTask.syncStatus == TaskEntity.SYNCED) {

                // A tarefa já existe localmente e pode receber os dados remotos.
                taskDao.updateTask(
                    localTask.copy(
                        title = remoteTask.title,
                        description = remoteTask.description,
                        isCompleted = remoteTask.isCompleted
                    )
                )
            }
        }

    }

    // Consulta as tarefas existentes no servidor.
    suspend fun getRemoteTasks(): List<RemoteTask> {
        return fakeServer.getTasks()
    }

    fun setServerOnline(isOnline: Boolean) {
        fakeServer.isOnline = isOnline
    }

    suspend fun createRemoteTestTask(
        title: String,
        description: String
    ) {
        fakeServer.createTask(
            title = title,
            description = description,
            isCompleted = false
        )
    }
}