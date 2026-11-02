package com.example.testeaula.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testeaula.data.TaskEntity
import com.example.testeaula.repository.TaskRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.testeaula.remote.RemoteTask

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks = repository.allTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isServerOnline = MutableStateFlow(true)
    val isServerOnline: StateFlow<Boolean> =
        _isServerOnline.asStateFlow()

    private val _isSynchronizing = MutableStateFlow(false)
    val isSynchronizing: StateFlow<Boolean> =
        _isSynchronizing.asStateFlow()

    private val _syncMessage = MutableStateFlow<String?>(null)
    val syncMessage: StateFlow<String?> =
        _syncMessage.asStateFlow()

    private val _remoteTasks = MutableStateFlow<List<RemoteTask>>(emptyList())

    val remoteTasks: StateFlow<List<RemoteTask>> =
        _remoteTasks.asStateFlow()

    fun createTask(title: String, description: String) {
        viewModelScope.launch {
            repository.createTask(title, description)
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.toggleTask(task)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun setServerOnline(isOnline: Boolean) {
        repository.setServerOnline(isOnline)
        _isServerOnline.value = isOnline

        _syncMessage.value = null
    }

    fun synchronize() {
        if (_isSynchronizing.value) return

        viewModelScope.launch {

            _isSynchronizing.value = true
            _syncMessage.value = "Sincronizando..."

            try {
                repository.synchronize()

                _remoteTasks.value = repository.getRemoteTasks()

                _syncMessage.value = "Sincronização concluída!"

            } catch (exception: CancellationException) {
                throw exception

            } catch (exception: Exception) {
                _syncMessage.value =
                    "Erro: ${exception.message}"

            } finally {
                _isSynchronizing.value = false
            }
        }
    }
    fun createRemoteTestTask() {
        viewModelScope.launch {
            try {
                repository.createRemoteTestTask(
                    title = "Tarefa criada no servidor",
                    description = "Esta tarefa veio do servidor remoto."
                )

                _syncMessage.value = "Tarefa criada diretamente no servidor!"

            } catch (exception: CancellationException) {
                throw exception

            } catch (exception: Exception) {
                _syncMessage.value = "Erro: ${exception.message}"
            }
        }
    }
}