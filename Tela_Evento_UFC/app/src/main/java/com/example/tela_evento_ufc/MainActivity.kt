package com.example.tela_evento_ufc
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaUFC()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TelaUFCPreview(){
    TelaUFC()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaUFC(){
    var nome by remember { mutableStateOf("") }
    var inscrito by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Eventos UFC")})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) { Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text ("Workshop: Meu Primeiro App Android",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text("Aprenda os primeiros passos com Kotlin e Jetpack Compose",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text("15 de outubro as 14:00\nCampus de Russas - Laboratório 01",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
            Spacer(Modifier.height(10.dp))
            Text("Inscrição", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.surfaceTint)
            Text("Digite seu nome se deseja participar:")
            TextField(
                value = nome,
                onValueChange = {
                    nome = it
                    inscrito = false
                },
                label = {Text("Nome")}
            )
            Button(
                onClick = {inscrito = true},
                enabled = nome.isNotBlank(),
            ) {
                Text("Quero Participar")
            }
            if(inscrito){
                Registrado(nome)
            }
        }
    }
}

@Composable
fun Registrado(nome: String){
    Text(text = "Interesse registrado para $nome")
}