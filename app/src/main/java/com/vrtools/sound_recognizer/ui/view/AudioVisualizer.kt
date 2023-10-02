package com.vrtools.sound_recognizer.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vrtools.sound_recognizer.utils.calculateMaxAmplitude

@Composable
fun AudioVisualizer(audioData: ByteArray) {
    var maxAmplitude by remember { mutableStateOf(1) }

    LaunchedEffect(audioData) {
        maxAmplitude = calculateMaxAmplitude(audioData)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Max Amplitude: $maxAmplitude", style = MaterialTheme.typography.body1)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(maxAmplitude / 32767f)
                    .background(Color.Blue)
            )
        }
    }
}