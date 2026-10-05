package com.example.tela_restaurante_casa

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tela_restaurante_casa.ui.theme.Tela_Restaurante_CasaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaRestaurante()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaRestaurantePreview(){
    TelaRestaurante()
}

@Composable
fun TelaRestaurante(){
    Column(
        //Modifier foi ultilizado aqui, toda a tela fica dentro de um column
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var valorComida by remember { mutableFloatStateOf(40.50f) }
        var total by remember { mutableIntStateOf(1) }


        Text(text = "Sushi do Oyshy")
        Text(text = "O melhor do Sushi")
        Spacer(Modifier.height(16.dp))
        //Row foi ultilizado aqui na parte de avaliação e tempo de espera e também nos botões de aumentar e diminuir
        Row() {
            Text(text = "* Avaliação: 5.0")
            Spacer(Modifier.width(10.dp))
            Text(text = "Tempo de espera: 50-70 min")
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Sushi do dia: Hot de camarão")
        Spacer(Modifier.height(5.dp))
        Text(text = "R$ ${"%.2f".format(valorComida)}")
        Spacer(Modifier.height(10.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //o variavel total é modificada aqui
            Button(onClick = {if (total == 1) total = 1 else total--}) {
                Text(text = "-")
            }
            Spacer(Modifier.width(10.dp))
            Text("Quantidade: $total")
            Spacer(Modifier.width(10.dp))
            Button(onClick = {total++}) {
                Text(text = "+")
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(text = "Total: R$ ${"%.2f".format(valorComida*total)}")
        Spacer(Modifier.height(10.dp))
        Button(onClick = {}) {
            Text(text = "Fazer pedido")
        }

    }

}
