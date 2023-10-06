package com.vrtools.sound_recognizer.model

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.utils.SAMPLING_RATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AudioRecorder (private val activity: MainActivity){
    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLING_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    val data = ByteArray(bufferSize)
    var recorder: AudioRecord? = null
    private var isRecording = false
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

    private fun initRecorder() {
        recorder = if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED) null
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
            while(isRecording) {
                val readSize = recorder?.read(data, 0, bufferSize)
                println("Recording: ${isRecording}, Read size: $readSize")
                if (readSize != null && readSize != AudioRecord.ERROR_INVALID_OPERATION)
                    data.copyOfRange(0, readSize)
            }
        }
    }
}