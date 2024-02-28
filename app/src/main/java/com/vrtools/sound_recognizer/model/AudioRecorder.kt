package com.vrtools.sound_recognizer.model

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.utils.SAMPLING_RATE
import com.vrtools.sound_recognizer.utils.isPermissionsGainded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AudioRecorder (private val context: Context){
    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLING_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    val data = ByteArray(bufferSize)
    var recorder: AudioRecord? = null
    var isRecording = false
    private var dataReadRoutine: Job? = null

    fun startRecording() {
        if(isRecording) return
        println("start recording")
        initRecorder()
        recorder?.startRecording()
        isRecording = true
        initDataReadRoutine()
    }

    fun stopRecording() {
        println("stop recording")
        recorder?.stop()
        recorder?.release()
        recorder = null
        isRecording = false
        stopDataReadRoutine()
    }

    @SuppressLint("MissingPermission")
    private fun initRecorder() {
        recorder = if (!isPermissionsGainded(context)) null
        else AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLING_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )
    }

    private fun stopDataReadRoutine() {
        dataReadRoutine?.cancel()
    }

    private fun initDataReadRoutine() {
        dataReadRoutine?.cancel()
        dataReadRoutine = CoroutineScope(Dispatchers.IO).launch {
            println("Recording: $isRecording")
            while(false) {
                val readSize = recorder?.read(data, 0, bufferSize)
                println("Recording: ${isRecording}, Read size: $readSize")
                if (readSize != null && readSize != AudioRecord.ERROR_INVALID_OPERATION)
                    data.copyOfRange(0, readSize)
                        .convertToComplex()
                        .fft()
                        .divideToThirds()
            }
        }
    }
}