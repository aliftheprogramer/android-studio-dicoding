package com.example.myapplication.util

import android.content.Context
import android.widget.Toast

object ToastUtil {
    private var lastToastTime: Long = 0
    private var lastToast: Toast? = null
    private const val DEBOUNCE_DELAY = 2000 // 5 seconds

    fun showToast(context: Context, message: String) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastToastTime > DEBOUNCE_DELAY) {
            lastToast?.cancel()
            lastToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            lastToast?.show()
            lastToastTime = currentTime
        }
    }
}