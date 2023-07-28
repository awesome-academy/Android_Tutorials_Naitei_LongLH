package com.sun.android.screen.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sun.android.R
import com.sun.android.data.model.Song
import com.sun.android.databinding.ItemSongBinding

class SongAdapter(
    val context: Context,
    private val onSongItemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongsViewHolder>() {

    private var listSongs = ArrayList<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listSongs.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        val songData = listSongs[position]
        holder.bindData(songData, onSongItemClick)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(songs: ArrayList<Song>) {
        listSongs = songs
        notifyDataSetChanged()
    }

    inner class SongsViewHolder(val binding: ItemSongBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(song: Song, onSongItemClick: (Song) -> Unit) {
            // Check current Night Mode and set background color for RelativeLayout
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            val backgroundColor = if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                ContextCompat.getColor(context, R.color.night_background)
            } else {
                ContextCompat.getColor(context, R.color.gray) // Default color in Light Mode
            }

            binding.tvNameSong.text = song.title
            binding.tvNameSinger.text = song.artist

            binding.relativeLayoutSong.setBackgroundColor(backgroundColor)

            Glide.with(context)
                .load(song.image) // Use the album artwork URI from the Song object
                .placeholder(R.drawable.music_icon) // Replace with your default placeholder image resource
                .error(R.drawable.music_icon) // Replace with your error image resource
                .into(binding.imgSong)

            binding.relativeLayoutSong.setOnClickListener {
                onSongItemClick(song)
            }
        }
    }
}
