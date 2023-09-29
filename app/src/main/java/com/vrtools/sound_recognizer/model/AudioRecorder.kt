package com.vrtools.sound_recognizer.model

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.utils.SAMPLING_RATE
import java.util.concurrent.atomic.AtomicBoolean

class AudioRecorder (activity: MainActivity){
    private val bufferSize = AudioRecord.getMinBufferSize(
        SAMPLING_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )
    val data = ByteArray(bufferSize)
    var recorder: AudioRecord? = null
    var isRecording: AtomicBoolean = AtomicBoolean(false)

    init {
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

    @Synchronized
    fun startRecording() {
        if(isRecording.get()) return
        recorder?.startRecording()
        isRecording.set(true)
    }

    @Synchronized
    fun releaseRecording() {
        if(isRecording.get()) return
        recorder?.release()
        isRecording.set(true)
    }


    @Synchronized
    fun stopRecording() {
        if(!isRecording.get()) return
        recorder?.stop()
        isRecording.set(false)
    }

    fun readRecordedData(): Boolean {
        if(!isRecording.get() || recorder == null) return false
        val a = recorder!!.read(data, 0, bufferSize)
        return a <= 0
    }
}