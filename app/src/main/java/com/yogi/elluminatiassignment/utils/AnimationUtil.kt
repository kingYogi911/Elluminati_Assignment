package com.yogi.elluminatiassignment.utils

import android.os.Handler
import android.os.Looper
import com.google.android.material.button.MaterialButton


fun MaterialButton.setTextAnimation(text: String, duration: Long = 100) {
    if (this.text != text) {
        val handler = Handler(Looper.getMainLooper())
        val blinkRunnable = Runnable {
            this@setTextAnimation.text = ""
            handler.postDelayed({
                this@setTextAnimation.text = text
            }, duration) // Adjust the delay as needed
        }
        handler.post(blinkRunnable)
    }
}