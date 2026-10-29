package com.example.aulamarinawillian.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Text

@Composable
fun MarinaMentindo(navController: NavController){
    Button(onClick = { navController.popBackStack() }) {
        Text("Voltar")
    }

}