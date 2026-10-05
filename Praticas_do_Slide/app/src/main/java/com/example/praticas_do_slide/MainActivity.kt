package com.example.praticas_do_slide

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
import androidx.compose.ui.unit.dp
import com.example.praticas_do_slide.ui.theme.Praticas_do_SlideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaPerfil()
            TelaPerfilMaterial()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfil(){
    var nome by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Meu perfil")})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Dados pessoais",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = {Text("Nome")}
            )
            TextField(
                value = curso,
                onValueChange = { curso = it},
                label = {Text("Curso")}
            )
            Button(onClick = {}) {
                Text("Salvar")
            }
        }

    }
}

@Composable
fun TelaPerfilMaterial(){

}