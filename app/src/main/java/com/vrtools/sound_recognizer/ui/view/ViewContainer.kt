package com.vrtools.sound_recognizer.ui.view

import android.widget.Button
import com.vrtools.sound_recognizer.MainActivity
import com.vrtools.sound_recognizer.R

class ViewContainer (private val activity: MainActivity) {
    /*val graph: LegacyGraphView = activity.findViewById(R.id.graphView)
    private val aWeighting: Button = activity.findViewById(R.id.aWeighting)
    private val cWeighting: Button = activity.findViewById(R.id.cWeighting)

    init {
        initAWeightingButton()
        initCWeightingButton()
        initGraph()
    }

    private fun initGraph() {
        graph.post {
            graph.initView()
        }
    }

    private fun initAWeightingButton() {
        aWeighting.setOnClickListener {
            activity.runOnUiThread {
                graph.changeState()
            }
        }
        aWeighting.isActivated = true
        aWeighting.isEnabled = false
    }

    private fun initCWeightingButton() {
        cWeighting.setOnClickListener {
            activity.runOnUiThread {
                graph.changeState()
            }
        }
        cWeighting.isActivated = false
        cWeighting.isEnabled = true
    }*/
}