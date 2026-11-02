package com.example.testeaula

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.testeaula.data.TaskDatabase
import com.example.testeaula.remote.FakeTaskServer
import com.example.testeaula.repository.TaskRepository
import com.example.testeaula.ui.TaskScreen
import com.example.testeaula.ui.theme.TesteAulaTheme
import com.example.testeaula.viewmodel.TaskViewModel
import com.example.testeaula.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // 1. Inicializa o banco de dados local.
        val database = TaskDatabase.getDatabase(applicationContext)

        // 2. Cria o servidor remoto simulado.
        val fakeServer = FakeTaskServer()

        // 3. Cria o Repository.
        val repository = TaskRepository(
            taskDao = database.taskDao(),
            fakeServer = fakeServer
        )

        // 4. Cria o ViewModel utilizando a Factory.
        val factory = TaskViewModelFactory(repository)

        taskViewModel = ViewModelProvider(
            this,
            factory
        )[TaskViewModel::class.java]

        setContent {
            TesteAulaTheme {
                TaskScreen(taskViewModel)
                // Nossa interface será adicionada aqui.
            }
        }
    }
}