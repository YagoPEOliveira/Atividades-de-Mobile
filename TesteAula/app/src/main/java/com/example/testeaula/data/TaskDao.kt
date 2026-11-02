package com.example.testeaula.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    // Observa todas as tarefas, ordenadas pelas mais recentes.
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Insere uma nova tarefa.
    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    // Atualiza uma tarefa existente.
    @Update
    suspend fun updateTask(task: TaskEntity)

    // Exclui uma tarefa do banco local.
    @Delete
    suspend fun deleteTask(task: TaskEntity)

    // Busca tarefas que ainda precisam ser sincronizadas.
    @Query("SELECT * FROM tasks WHERE syncStatus != 0")
    suspend fun getTasksPendingSync(): List<TaskEntity>

    // Busca uma tarefa pelo identificador local.
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    // Insere uma tarefa recebida do servidor ou substitui a existente.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE remoteId = :remoteId LIMIT 1")
    suspend fun getTaskByRemoteId(remoteId: Long): TaskEntity?
}