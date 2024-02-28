package com.vrtools.sound_recognizer.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vrtools.sound_recognizer.utils.AUDIO_RECORD_MAX_VALUE
import com.vrtools.sound_recognizer.model.calculateMaxAmplitude

@Composable
fun AudioVisualizer(audioData: ByteArray) {
    var maxAmplitude by remember { mutableStateOf(0) }

    LaunchedEffect(audioData) {
        maxAmplitude = calculateMaxAmplitude(audioData)
        println("Amplitude: $maxAmplitude")
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
                    .fillMaxHeight(maxAmplitude / (AUDIO_RECORD_MAX_VALUE - 1).toFloat())
                    .background(Color.Blue)
            ) {
                Graph(data = audioData)
            }
        }
    }
}