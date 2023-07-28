package com.sun.android.services

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.android.R
import com.sun.android.data.model.Song
import com.sun.android.utils.MyApplication
import com.sun.android.utils.SongReceiver
import com.sun.android.utils.constants.*

class SongService : Service() {

    private var isPlaying = false
    private var song = Song()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bundle = intent?.extras
        if (bundle != null) {
            song = bundle.get(OBJECT_SONG_KEY) as Song
            isPlaying = bundle.getBoolean(STATUS_PLAYER_KEY)
            val actionMusic = intent.getIntExtra(ACTION_MUSIC_SERVICE_KEY, 0)

            if (actionMusic != 0) {
                handleActionMusic(actionMusic)
            } else {
                startPlayMusic(song)
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    private fun startPlayMusic(song: Song) {
        if (isPlaying) {
            sendBroadcastToMainActivity(ACTION_START, song)
            sendNotificationMedia(song)
        }
    }

    private fun sendBroadcastToMainActivity(action: Int, song: Song) {
        val intent = Intent(SEND_ACTION_TO_LIST_SONG_KEY)

        val bundle = Bundle().apply {
            putParcelable(OBJECT_SONG_KEY, song)
            putBoolean(STATUS_PLAYER_KEY, isPlaying)
            putInt(ACTION_MUSIC_KEY, action)
        }

        intent.putExtras(bundle)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun handleActionMusic(action: Int) {
        when (action) {
            ACTION_PAUSE -> pauseMusic()
            ACTION_RESUME -> resumeMusic()
            ACTION_PLAY_NEXT_SONG -> playNextSong()
            ACTION_PLAY_PREVIOUS_SONG -> playPreviousSong()
        }
    }

    private fun pauseMusic() {
        sendNotificationMedia(song)
    }

    private fun resumeMusic() {
        sendNotificationMedia(song)
    }

    private fun playNextSong() {
        sendNotificationMedia(song)
    }

    private fun playPreviousSong() {
        sendNotificationMedia(song)
    }

    private fun sendNotificationMedia(song: Song) {
        // Load the album artwork from the Uri provided in the song.image property
        val bitmap: Bitmap? = song.image?.let {
            try {
                val inputStream = contentResolver.openInputStream(it)
                BitmapFactory.decodeStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } ?: BitmapFactory.decodeResource(resources, R.drawable.default_circle_music) // Fallback bitmap if null

        val mediaMetaData = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, song.title)
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, song.artist)
            .build()

        // Create a MediaSessionCompat
        val mediaSessionCompat = MediaSessionCompat(this, MEDIA_TAG).apply {
            setMetadata(mediaMetaData)
            isActive = true
        }

        val notificationBuilder = NotificationCompat.Builder(this, MyApplication.CHANNEL_PLAY_MUSIC)
            // Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_music_play)
            .setLargeIcon(bitmap)
            .setSubText(getString(R.string.simple_music_app))
            .setContentTitle(song.title)
            .setContentText(song.artist)
            // Apply the media style template
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediaSessionCompat.sessionToken)
            )

        if (isPlaying) {
            notificationBuilder
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.black_skip_previous_24, getString(R.string.previous), getPendingIntent(this, ACTION_PLAY_PREVIOUS_SONG)) // #0
                .addAction(R.drawable.black_pause_24, getString(R.string.pause), getPendingIntent(this, ACTION_PAUSE)) // #1
                .addAction(R.drawable.black_skip_next_24, getString(R.string.next), getPendingIntent(this, ACTION_PLAY_NEXT_SONG)) // #2
                .addAction(R.drawable.black_close_24px, getString(R.string.clear), getPendingIntent(this, ACTION_CLEAR)) // #3
        } else {
            notificationBuilder
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.black_skip_previous_24, getString(R.string.previous), getPendingIntent(this, ACTION_PLAY_PREVIOUS_SONG)) // #0
                .addAction(R.drawable.black_play_circle_24px, getString(R.string.resume), getPendingIntent(this, ACTION_RESUME)) // #1
                .addAction(R.drawable.black_skip_next_24, getString(R.string.next), getPendingIntent(this, ACTION_PLAY_NEXT_SONG)) // #2
                .addAction(R.drawable.black_close_24px, getString(R.string.clear), getPendingIntent(this, ACTION_CLEAR)) // #3
        }

        startForeground(1, notificationBuilder.build())
    }

    private fun getPendingIntent(context: Context, action: Int): PendingIntent {
        val intent = Intent(this, SongReceiver::class.java)
        val bundle = Bundle().apply {
            putParcelable(OBJECT_SONG_KEY, song)
            putBoolean(STATUS_PLAYER_KEY, action == ACTION_PAUSE)
        }
        intent.putExtra(ACTION_MUSIC_KEY, action)
        intent.putExtras(bundle)

        return PendingIntent.getBroadcast(
            context.applicationContext,
            action,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val ACTION_PAUSE = 1
        const val ACTION_RESUME = 2
        const val ACTION_CLEAR = 3
        const val ACTION_START = 4
        const val ACTION_PLAY_NEXT_SONG = 5
        const val ACTION_PLAY_PREVIOUS_SONG = 6
        const val MEDIA_TAG = "Tag"
    }
}
