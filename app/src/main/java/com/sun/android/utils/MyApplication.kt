package com.sun.android.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.sun.android.R

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val songChannel = NotificationChannel(
                CHANNEL_PLAY_MUSIC,
                getString(R.string.channel_play_music),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(null, null)
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(songChannel)
        }
    }

    companion object {
        const val CHANNEL_PLAY_MUSIC = "Channel Play Music"
    }
}
