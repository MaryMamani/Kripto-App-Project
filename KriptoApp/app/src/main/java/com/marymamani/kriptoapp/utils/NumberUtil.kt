package com.marymamani.kriptoapp.utils

import kotlin.random.Random

object NumberUtils {

    fun calculatePercentage(part: Int, total: Int): Float {
        return if (total > 0) {
            val percentage = (part.toDouble() / total * 100)
            String.format("%.2f", percentage).toFloat()
        } else {
            0f
        }
    }

    fun getRandomValue(value: Int): Int {
        if(value <= 0) {
            return Random.nextInt(1, 40)
        } else {
            return value
        }
    }
}