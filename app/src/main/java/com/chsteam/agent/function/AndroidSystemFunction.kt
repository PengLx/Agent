package com.chsteam.agent.function

import android.annotation.SuppressLint



object AndroidSystemFunction {
    object AlarmManager {
        @SuppressLint("ServiceCast")
        fun createAlarm() : Boolean {
            return true
        }
    }
}