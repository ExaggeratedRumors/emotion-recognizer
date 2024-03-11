package com.ertools.sound_recognizer.model

interface SpectrumProvider {
    fun getAmplitudeSpectrum(): IntArray
    fun getMaxAmplitude(): Int
}