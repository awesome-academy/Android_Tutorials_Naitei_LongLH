package com.sun.android.data.repository.source

import android.content.ContentResolver
import com.sun.android.data.model.Song

interface SongDataSource {
    interface Local {
        fun getSongsLocal(contentResolver: ContentResolver, listener: OnResultListener<MutableList<Song>>)
    }
}
