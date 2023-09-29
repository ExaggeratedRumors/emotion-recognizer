package com.vrtools.sound_recognizer.utils

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.math.*

const val AUDIO_RECORD_MAX_VALUE = 32768
const val SAMPLING_RATE = 44100
const val FFT_SIZE = 1024
const val THIRDS_NUMBER = 33
const val CALIBRATION = 20
const val BASIC_FREQUENCY = 12.5
const val RECORD_DELAY = 85L


enum class State { A_WEIGHTING, C_WEIGHTING }

fun cutoffFrequency(numberOfTerce: Int) =
    BASIC_FREQUENCY * 2f.pow((2f * numberOfTerce - 1) / 6f)

fun middleFrequency(numberOfTerce: Int) =
    BASIC_FREQUENCY * 2f.pow((numberOfTerce - 1) / 3f)

fun weightingA(numberOfTerce: Int) : Int {
    val f = middleFrequency(numberOfTerce)
    val ra = (12200f.pow(2) * f.pow(4)) / (
            (f.pow(2) + 20.6.pow(2)) *
            (f.pow(2) + 12200f.pow(2)) *
            sqrt(f.pow(2) + 107.7.pow(2)) *
            sqrt(f.pow(2) + 737.9.pow(2))
            )
    return (20 * log10(ra) + 2).toInt()
}

fun weightingC(numberOfTerce: Int) : Int {
    val f = middleFrequency(numberOfTerce)
    val rc = (12200f.pow(2) * f.pow(2)) / (
            (f.pow(2) + 20.6.pow(2)) *
            (f.pow(2) + 12200f.pow(2))
            )
    return (20 * log10(rc) + 0.06).toInt()
}

fun convertToDecibels(amplitude: Float) =
    (10 * log10(max(0.5f, amplitude))).toInt()

fun ByteArray.convertToComplex() = Array(FFT_SIZE) {
        if (it > this.size) Complex()
        else Complex(1.0 * CALIBRATION / AUDIO_RECORD_MAX_VALUE * (
                this[2 * it].toShort() and 0xFF or (this[2 * it + 1].toInt() shl 8).toShort()
                )
        )
    }

fun Array<Complex>.fft(): Array<Complex> {
    val n = this.size
    if(n == 1) return Array(n) { this[0] }
    if(n % 2 != 0) {
        throw java.lang.RuntimeException("N is not a power of 2")
    }

    val temp = Array(n / 2) { Complex() }
    for(i in 0 until n / 2)
        temp[i] = this[2 * i]
    val q = temp.fft()

    for(i in 0 until n / 2)
        temp[i] = this[2 * i + 1]
    val r = temp.fft()

    val y = Array(n) { Complex() }
    for(i in 0 until n / 2) {
        val kth = -2 * i * PI / n
        val wk = Complex(cos(kth), sin(kth))
        y[i] = q[i].plus(wk.times(r[i]))
        y[i + n / 2] = q[i].minus(wk.times(r[i]))
    }
    return y
}

fun Array<Complex>.divideToThirds(): IntArray{
    val amplitudeData = IntArray(THIRDS_NUMBER) { 0 }
    val cutoffFreq33 = cutoffFrequency(THIRDS_NUMBER)
    val freqWindow = SAMPLING_RATE.toFloat() / FFT_SIZE
    var terce = 1
    var iterator = 0
    var accumulated = Complex()
    while (iterator < this.size) {
        if((iterator * freqWindow) > cutoffFreq33 || terce > THIRDS_NUMBER) break
        accumulated += this[iterator]
        if((iterator + 1) * freqWindow > cutoffFrequency(terce) &&
            iterator * freqWindow < SAMPLING_RATE / 2f) {
            terce += 1
            continue
        }
        val absValue = accumulated.real.pow(2) + accumulated.imag.pow(2)
        if(absValue > amplitudeData[terce - 1])
            amplitudeData[terce - 1] = absValue.toInt()
        accumulated = Complex()
        iterator += 1
    }
    return amplitudeData
}

fun getDamping(terce: Int, state: State): Int {
    return when(state) {
        State.A_WEIGHTING -> weightingA(terce)
        State.C_WEIGHTING -> weightingC(terce)
    }
}

fun getDbSignalForm(amplitudeData: IntArray, state: State): IntArray {
    val newData = IntArray(THIRDS_NUMBER)
    for(i in 0 until THIRDS_NUMBER)
        newData[i] = max(0, getDamping(i, state) +
                convertToDecibels(amplitudeData[i].toFloat())
        )
    return newData
}