package com.example.aulamarinawillian.view


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun Home(navController: NavController){
    Button(onClick = { navController.navigate("MarinaMentindo") }) {
        Text("Ir para MarinaMentindo")
    }
}