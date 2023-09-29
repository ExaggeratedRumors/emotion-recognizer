package com.vrtools.sound_recognizer.model

import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.view.ViewContainer

class RecordingHandler (
    private val activity: MainActivity,
    private val components: ViewContainer
) {
    fun dataChangeNotification(data: IntArray) {
        activity.runOnUiThread {
            components.graph.invalidate(data)
        }
    }
}