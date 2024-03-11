package com.vrtools.sound_recognizer.view

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vrtools.sound_recognizer.model.AudioRecorder
import com.vrtools.sound_recognizer.model.DampingState
import com.vrtools.sound_recognizer.model.SpectrumProvider
import com.vrtools.sound_recognizer.model.getDbSignalForm
import com.vrtools.sound_recognizer.utils.INVALIDATE_GUI_DELAY
import com.vrtools.sound_recognizer.utils.Logger
import kotlinx.coroutines.delay

@Composable
fun AudioVisualizer(context: Context) {
    val recorder = remember { AudioRecorder(context) }
    val isRecording = remember { mutableStateOf(false) }
    val dampingState = remember { mutableStateOf(DampingState.A_WEIGHTING) }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            ) {
            RecordButton(recorder, isRecording)
            Spacer(modifier = Modifier.width(50.dp))
            DampingButton(dampingState)

        }
        Spacer(modifier = Modifier.height(50.dp))
        Graph(provider = recorder, state = dampingState)
    }
}

@Composable
fun RecordButton(recorder: AudioRecorder, isRecording: MutableState<Boolean>) {
    Button(
        onClick = {
            isRecording.value = if (isRecording.value) {
                recorder.stopRecording()
                false
            } else {
                recorder.startRecording()
                true
            }
        },
        modifier = Modifier.size(120.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isRecording.value) Color.Red else Color.Green
        )
    ) {
        Text(text = if (isRecording.value) "Stop" else "Record")
    }
}

@Composable
fun DampingButton(dampingState: MutableState<DampingState>) {
    Button(
        onClick = {
            if(dampingState.value == DampingState.A_WEIGHTING) {
                dampingState.value = DampingState.C_WEIGHTING
            } else {
                dampingState.value = DampingState.A_WEIGHTING
            }
        },
        modifier = Modifier.size(120.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (dampingState.value == DampingState.A_WEIGHTING)
                Color.hsv(180F, 0.25F, 0.5F)
            else
                Color.hsv(75F, 0.33F, 0.75F)
        )
    ) {
        Text(
            text = if (dampingState.value == DampingState.A_WEIGHTING)
                "A FILTER"
            else
                "C FILTER"
        )
    }
}


@Composable
fun Graph (provider: SpectrumProvider, state: MutableState<DampingState>) {
    var spectrum by remember { mutableStateOf(provider.getAmplitudeSpectrum()) }

    LaunchedEffect(key1 = true) {
        while (true) {
            spectrum = getDbSignalForm(provider.getAmplitudeSpectrum(), state.value)
            delay(INVALIDATE_GUI_DELAY)
            Logger.logSpectrum(spectrum)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.LightGray)
    ) {
        spectrum.forEach { sample ->
            val animatedHeight by animateFloatAsState(
                targetValue = sample.toFloat(),
                animationSpec = spring(), label = "$sample"
            )
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height((animatedHeight).dp)
                    .background(Color.Blue)
                    .align(Alignment.Bottom)
            )
            Spacer(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
            )
        }
    }
}