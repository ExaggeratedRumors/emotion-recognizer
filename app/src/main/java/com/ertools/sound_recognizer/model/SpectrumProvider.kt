package com.ertools.emotion_recognizer.model

interface SpectrumProvider {
    fun getAmplitudeSpectrum(): IntArray
    fun getMaxAmplitude(): Int
}