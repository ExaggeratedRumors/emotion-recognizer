package com.vrtools.sound_recognizer.view


import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vrtools.sound_recognizer.model.AudioRecorder

@Composable
fun RecognizerApp () {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Audio Recorder", style = MaterialTheme.typography.h4)
        val context = LocalContext.current
        val recorder = remember { AudioRecorder(context) }
        var isRecording by remember { mutableStateOf(false) }

        RecordButton(
            onClick = {
                isRecording = if (isRecording) {
                    recorder.stopRecording()
                    false
                } else {
                    recorder.startRecording()
                    true
                }
            },
            isRecording = isRecording
        )
        AudioVisualizer(recorder)
    }
}