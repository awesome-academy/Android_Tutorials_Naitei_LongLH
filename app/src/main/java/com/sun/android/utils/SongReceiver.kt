package com.sun.android.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.android.data.model.Song
import com.sun.android.utils.constants.*

class SongReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intentMusicPlayerReceiver = Intent(SEND_ACTION_TO_MUSIC_PLAYER_KEY)

        val bundle = intent?.extras
        if (bundle != null) {
            val song = bundle.get(OBJECT_SONG_KEY) as? Song
            val isPlaying = bundle.getBoolean(STATUS_PLAYER_KEY)
            val actionMusic = intent.getIntExtra(ACTION_MUSIC_KEY, 0)

            val bundleReceiver = Bundle().apply {
                putParcelable(OBJECT_SONG_KEY, song)
                putBoolean(STATUS_PLAYER_KEY, isPlaying)
                putInt(ACTION_MUSIC_SERVICE_KEY, actionMusic)
            }
            intentMusicPlayerReceiver.putExtras(bundleReceiver)
        }

        LocalBroadcastManager.getInstance(context).sendBroadcast(intentMusicPlayerReceiver)
    }
}
