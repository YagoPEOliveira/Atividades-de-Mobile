package com.example.testeaula.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testeaula.data.TaskEntity
import com.example.testeaula.viewmodel.TaskViewModel

@Composable
fun TaskScreen(viewModel: TaskViewModel) {

    val tasks by viewModel.tasks.collectAsState()
    val isServerOnline by viewModel.isServerOnline.collectAsState()
    val isSynchronizing by viewModel.isSynchronizing.collectAsState()
    val syncMessage by viewModel.syncMessage.collectAsState()
    val remoteTasks by viewModel.remoteTasks.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "Gerenciador de tarefas",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Status do servidor
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = if (isServerOnline) {
                            "Servidor: ONLINE"
                        } else {
                            "Servidor: OFFLINE"
                        },
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.setServerOnline(!isServerOnline)
                        },
                        enabled = !isSynchronizing,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (isServerOnline) {
                                "Desligar servidor"
                            } else {
                                "Ligar servidor"
                            }
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.synchronize()
                        },
                        enabled = isServerOnline && !isSynchronizing,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (isSynchronizing) {
                                "Sincronizando..."
                            } else {
                                "Sincronizar tarefas"
                            }
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.createRemoteTestTask()
                        },
                        enabled = isServerOnline && !isSynchronizing,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Criar tarefa diretamente no servidor")
                    }

                    if (syncMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = syncMessage!!,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Cadastro de tarefas
            Text(
                text = "Nova tarefa",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título da tarefa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.createTask(
                            title = title.trim(),
                            description = description.trim()
                        )

                        title = ""
                        description = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar tarefa")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Minhas tarefas (${tasks.size})",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tarefas no servidor: ${remoteTasks.size}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (tasks.isEmpty()) {
                Text("Nenhuma tarefa cadastrada.")
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = tasks,
                    key = { it.id }
                ) { task ->

                    TaskItem(
                        task = task,
                        onToggle = {
                            viewModel.toggleTask(task)
                        },
                        onDelete = {
                            viewModel.deleteTask(task)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: TaskEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = {
                    onToggle()
                },
                enabled = task.syncStatus != TaskEntity.SYNC_DELETE_PENDING
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium
                )

                if (task.description.isNotBlank()) {
                    Text(text = task.description)
                }

                Spacer(modifier = Modifier.height(4.dp))

                val syncText = when (task.syncStatus) {
                    TaskEntity.SYNCED -> "Sincronizada"
                    TaskEntity.SYNC_PENDING -> "Pendente de sincronização"
                    TaskEntity.SYNC_DELETE_PENDING -> "Exclusão pendente"
                    else -> "Status desconhecido"
                }

                Text(
                    text = syncText,
                    style = MaterialTheme.typography.bodySmall
                )

                if (task.remoteId != null) {
                    Text(
                        text = "ID remoto: ${task.remoteId}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            TextButton(
                onClick = onDelete
            ) {
                Text("Excluir")
            }
        }
    }
}