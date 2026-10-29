package com.example.aulacaiquethiago

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aulacaiquethiago.ui.theme.AulaCaiqueThiagoTheme


@Composable
fun HelloScreen() { // dono do estado
    var name by rememberSaveable { mutableStateOf("") }
    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) { // só desenha
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

// ---------- rememberSaveable (sobrevive à morte do processo) ----------
@Composable
fun Lembrar() {
    var nome by rememberSaveable { mutableStateOf("") }
    Text(text = nome, fontSize = 18.sp)
    Button(onClick = { nome = "Alberto" }) {
        Text("Alterar estado")
    }
}

// ---------- StateFlow + UiState (igual ao slide) ----------
@Composable
fun HomeScreen(vm: MainViewModel = viewModel()) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.items.forEach { Text(it) }

        state.error?.let { Text(it) }

        Text(text = state.contador.toString(), fontSize = 24.sp)
        Button(onClick = { vm.aumentar() }) { Text("Aumentar") }
        Button(onClick = { vm.carregarItens() }) { Text("Carregar itens") }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AulaCaiqueThiagoTheme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HelloScreen()
                        Lembrar()
                        HomeScreen()
                    }
                }
            }
        }
    }
}
