package com.vrtools.sound_recognizer.ui.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.model.AudioRecorder

@Composable
fun RecognizerApp (activity: MainActivity) {
    val hasAudioPermission = ContextCompat.checkSelfPermission(
        activity, Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED
    var isRecording by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Audio Recorder", style = MaterialTheme.typography.h4)
        val recorder = AudioRecorder(activity)
        if (hasAudioPermission) {
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

            AudioVisualizer(recorder.data)
        } else {
            Text(
                text = "Please grant permission to record audio",
                style = MaterialTheme.typography.body1
            )
            Button(
                onClick = {
                    activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                        if (isGranted) {
                            recorder.startRecording()
                        } else {
                            // Handle permission denied
                        }
                    }.launch(Manifest.permission.RECORD_AUDIO)
                }
            ) {
                Text(text = "Grant Permission")
            }
        }
    }
}