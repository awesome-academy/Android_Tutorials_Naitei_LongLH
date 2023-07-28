package com.sun.android.data.repository.source.local

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import com.sun.android.data.model.Song
import com.sun.android.data.repository.source.OnResultListener
import com.sun.android.data.repository.source.SongDataSource
import com.sun.android.screen.SongMainActivity
import java.util.concurrent.Executors

class SongLocalDataSource: SongDataSource.Local {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getSongsLocal(contentResolver: ContentResolver, listener: OnResultListener<MutableList<Song>>) {
        executor.execute {
            val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val cursor = contentResolver.query(songUri, null, null, null, null)
            val listSongs = mutableListOf<Song>()

            if (cursor != null && cursor.moveToFirst()) {
                val songId = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
                val songTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val songArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val songData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)
                val songDate = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED)
                val songDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)
                val songAlbumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)

                while (cursor.moveToNext()) {
                    val currentId = cursor.getLong(songId)
                    val title = cursor.getString(songTitle)
                    val artist = cursor.getString(songArtist)
                    val data = cursor.getString(songData)
                    val date = cursor.getLong(songDate)
                    val duration = cursor.getInt(songDuration)
                    val albumId = cursor.getLong(songAlbumColumn)

                    val imageUri = Uri.parse(SongMainActivity.ALBUM_ART)
                    val albumUri = ContentUris.withAppendedId(imageUri, albumId)

                    val song = Song(
                        id = currentId,
                        path = data,
                        title = title,
                        artist = artist,
                        duration = duration,
                        date = date,
                        image = albumUri
                    )

                    listSongs.add(song)
                }
                cursor.close()
            }
            listener.onSuccess(listSongs)
        }
    }

    companion object {
        private var instance: SongLocalDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: SongLocalDataSource().also { instance = it }
        }
    }
}
