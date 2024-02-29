package com.vrtools.sound_recognizer.model

interface SpectrumProvider {
    fun getAmplitudeSpectrum(): IntArray
    fun getMaxAmplitude(): Int
}