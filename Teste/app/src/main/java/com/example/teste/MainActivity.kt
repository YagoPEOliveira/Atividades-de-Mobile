package com.example.teste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            /*Saudacao()
            MeuBotao()*/
//            Coluna()
            TelaPerfil()
//            Contador()
//            Imagem()
//            TelaPerfilPreview()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TelaPerfilPreview(){
    TelaPerfil()
}

@Composable
fun TelaPerfil(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "PERFIL DO ALUNO")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "João da Silva")
        Text(text = "Engenharia de Software")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {}) {
            Text(text = "Ver Perfil")
        }
        var contador by remember { mutableIntStateOf(0) }
        Button(onClick = {contador++}) {
            Text(text = "Cliques: $contador")
        }
    }
}
@Composable
fun Contador(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var contador by remember { mutableIntStateOf(0) }
        Button(onClick = {contador++}) {
            Text(text = "Cliques: $contador")
        }
    }

}

/*@Composable
fun Saudacao() {
    Text(text = "Hello World!")
}
@Composable
fun MeuBotao(){
    Button(onClick = {

    }) {
        Text(text = "Rapaaaaaaaaaaaz ")
    }
}*/
@Composable
fun Coluna(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Em cima?")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Em baixo?")
    }
    /*Box {
        Row {
            Button(onClick = {}) { Text(text = "Aba")}
            Button(onClick = {}) { Text(text = "Bab")}
            Text(text = "Sim")
            Text(text = "Não")
        }
    }*/

}


/*
@Composable
fun Imagem(){
    Image(
        painter = painterResource(id = R.drawable.android_logo)
    )
}*/
