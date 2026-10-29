package com.example.auladavi.ui.theme.sensores

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun SensorCamera() {
    val context = LocalContext.current
    var foto by remember { mutableStateOf<Bitmap?>(null) }

    val gatilho = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap == null) return@rememberLauncherForActivityResult
        foto = bitmap
    }

    val permissao = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) gatilho.launch(null)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                gatilho.launch(null)
            } else {
                permissao.launch(Manifest.permission.CAMERA)
            }
        }) { Text("Abrir câmera") }

        val f = foto
        if (f != null) {
            Image(bitmap = f.asImageBitmap(), contentDescription = "Foto capturada")
        }
    }
}








