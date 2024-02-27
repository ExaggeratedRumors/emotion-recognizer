package com.vrtools.sound_recognizer.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RecordButton(onClick: () -> Unit, isRecording: Boolean) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(120.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isRecording) Color.Red else Color.Green
        )
    ) {
        Text(text = if (isRecording) "Stop" else "Record")
    }
}