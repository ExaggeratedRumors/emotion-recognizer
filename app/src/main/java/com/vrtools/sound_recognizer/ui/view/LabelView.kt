package com.vrtools.sound_recognizer.ui.view

import android.content.Context
import android.view.View
import com.vrtools.sound_recognizer.utils.LABELS_REFRESH_DELAY

class LabelView (context: Context): View(context) {
    private var counter = 0
    var value = 0

    fun onRefresh(newValue: Int) {
        if(newValue > value || counter == 0) {
            counter = LABELS_REFRESH_DELAY
            value = newValue
        }
        else counter--
    }
}