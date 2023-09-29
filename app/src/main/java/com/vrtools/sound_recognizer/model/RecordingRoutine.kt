package com.vrtools.sound_recognizer.model

import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.view.ViewContainer
import com.vrtools.sound_recognizer.utils.*
import com.vrtools.sound_recognizer.utils.RECORD_DELAY
import com.vrtools.sound_recognizer.utils.State
import com.vrtools.sound_recognizer.utils.getDbSignalForm
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class RecordingRoutine (
    activity: MainActivity,
    components: ViewContainer
) {
    private val handler = RecordingHandler(activity, components)
    private val recorder = AudioRecorder(activity)

    fun start() {
        recordingService()
    }

    fun release() {
        recordingService()
    }

    fun stop() {
        recorder.stopRecording()
    }

    private fun recordingService() = thread (isDaemon = true) {
        recorder.startRecording()
        while(recorder.isRecording.get()) {
            sleep(RECORD_DELAY)
            if(recorder.readRecordedData()) continue
            val data = recorder.data
                .convertToComplex()
                .fft()
                .divideToThirds()
            handler.dataChangeNotification(getDbSignalForm(data, State.A_WEIGHTING))
        }
    }
}