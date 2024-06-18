package com.ertools.emotion_recognizer.core.recorder

interface SpectrumProvider {
    fun getAmplitudeSpectrum(): IntArray
    fun getMaxAmplitude(): Int
}