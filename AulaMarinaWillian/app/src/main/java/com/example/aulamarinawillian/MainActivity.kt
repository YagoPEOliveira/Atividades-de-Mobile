package com.example.aulamarinawillian

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
import com.example.aulamarinawillian.ui.theme.AulaMarinaWillianTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aulamarinawillian.view.Home
import com.example.aulamarinawillian.view.MarinaMentindo
import androidx.navigation.compose.NavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AulaMarinaWillianTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "Home",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(
                            route = "Home"
                        ){
                            Home(navController)
                        }
                        composable(
                            route = "MarinaMentindo"
                        ){
                            MarinaMentindo(navController)
                        }
                    }
                }
            }
        }
    }
}
