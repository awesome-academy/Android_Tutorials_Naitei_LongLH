package com.sun.android.data.repository

import android.content.ContentResolver
import com.sun.android.data.model.Song
import com.sun.android.data.repository.source.OnResultListener
import com.sun.android.data.repository.source.SongDataSource

class SongRepository private constructor(
    private val local: SongDataSource.Local
) : SongDataSource.Local {

    override fun getSongsLocal(contentResolver: ContentResolver, listener: OnResultListener<MutableList<Song>>) {
        local.getSongsLocal(contentResolver, listener)
    }

    companion object {
        private var instance: SongRepository? = null

        fun getInstance(local: SongDataSource.Local): SongRepository {
            return synchronized(this) {
                instance ?: SongRepository(local).also {
                    instance = it
                }
            }
        }
    }
}
