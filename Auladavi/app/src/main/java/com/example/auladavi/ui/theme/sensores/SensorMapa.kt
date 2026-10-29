package com.example.auladavi.ui.theme.sensores

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.android.gms.location.LocationServices
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.tan

fun calcularTileX(longitude: Double): Int {
    val n = 32768
    return ((longitude + 180.0) / 360.0 * n).toInt()
}

fun calcularTileY(latitude: Double): Int {
    val n = 32768
    val r = latitude * PI / 180.0
    return ((1.0 - ln(tan(r) + 1.0 / cos(r)) / PI) / 2.0 * n).toInt()
}

fun montarUrlMapa(latitude: Double, longitude: Double): String {
    val x = calcularTileX(longitude)
    val y = calcularTileY(latitude)
    return "https://tile.openstreetmap.org/15/$x/$y.png"
}

@Composable
fun SensorMapa(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var lat by remember { mutableStateOf<Double?>(null) }
    var lng by remember { mutableStateOf<Double?>(null) }
    val cliente = remember { LocationServices.getFusedLocationProviderClient(context) }

    fun guardarLocalizacao(loc: Location?) {
        if (loc == null) {
            return
        }
        lat = loc.latitude
        lng = loc.longitude
    }

    fun buscarLocalizacao() {
        cliente.lastLocation.addOnSuccessListener { loc -> guardarLocalizacao(loc) }
    }

    fun temPermissao(): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    fun aoReceberPermissao(foiPermitida: Boolean) {
        if (foiPermitida == false) {
            return
        }
        buscarLocalizacao()
    }

    val permissao = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { foiPermitida -> aoReceberPermissao(foiPermitida) }

    fun aoClicarBotao() {
        if (temPermissao()) {
            buscarLocalizacao()
        } else {
            permissao.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Button(onClick = { aoClicarBotao() }) {
            Text("Mostrar localização")
        }

        val currentLat = lat
        val currentLng = lng
        if (currentLat == null || currentLng == null) {
            return
        } else {
            val url = montarUrlMapa(currentLat, currentLng)

            Card(modifier = Modifier.fillMaxWidth().height(300.dp).padding(top = 8.dp)) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context)
                            .data(url)
                            .addHeader("User-Agent", "SensoresDemo/1.0")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Mapa",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text("📍", fontSize = 28.sp, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
