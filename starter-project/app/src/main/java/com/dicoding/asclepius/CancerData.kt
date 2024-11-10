package com.dicoding.asclepius

import android.annotation.SuppressLint
import java.io.Serializable

data class CancerData(
    val label: String,
    val score: Float
) : Serializable {
    @SuppressLint("DefaultLocale")
    fun getScoreAsPercentage(): String {
        return String.format("%.2f%%", score * 100)
    }
}