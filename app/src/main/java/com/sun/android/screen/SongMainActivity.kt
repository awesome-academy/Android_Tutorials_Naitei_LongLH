package com.sun.android.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.android.R
import com.sun.android.data.model.Song
import com.sun.android.data.repository.SongRepository
import com.sun.android.data.repository.source.OnResultListener
import com.sun.android.data.repository.source.SongDataSource
import com.sun.android.data.repository.source.local.SongLocalDataSource
import com.sun.android.databinding.ActivitySongMainBinding
import com.sun.android.screen.adapter.SongAdapter
import com.sun.android.services.SongService
import com.sun.android.utils.constants.*
import java.lang.Exception

class SongMainActivity : AppCompatActivity() {

    private val binding: ActivitySongMainBinding by lazy {
        ActivitySongMainBinding.inflate(layoutInflater)
    }

    private lateinit var song: Song
    private lateinit var sharedPreferences: SharedPreferences
    private var isPlaying = false
    private var actionMusic = 0
    private var listSongs = ArrayList<Song>()
    private val localDataSource: SongDataSource.Local = SongLocalDataSource.getInstance()
    private lateinit var songRepository: SongRepository

    private val songAdapter: SongAdapter by lazy {
        SongAdapter(this) { songData ->
            startPlayMusic(songData)
        }.apply {
            setData(listSongs)
        }
    }

    private val listSongBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val bundle = intent?.extras ?: return
            song = bundle.getParcelable<Song>(OBJECT_SONG_KEY) as Song
            isPlaying = bundle.getBoolean(STATUS_PLAYER_KEY)
            actionMusic = bundle.getInt(ACTION_MUSIC_KEY)

            showMusicPlayer(song)
        }
    }

    private val musicPlayerActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                isPlaying = data?.getBooleanExtra(STATUS_PLAYER_KEY, false) ?: false
            }
        }

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            listSongBroadcastReceiver, IntentFilter(SEND_ACTION_TO_LIST_SONG_KEY)
        )

        songRepository = SongRepository.getInstance(localDataSource)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        isPlaying = sharedPreferences.getBoolean(STATUS_PLAYER_KEY, false)

        if (savedInstanceState != null && savedInstanceState.containsKey(SONG_INFO)) {
            song = savedInstanceState.getParcelable<Song>(SONG_INFO) as Song
        }

        if (!checkPermission()) {
            requestPermission()
            return
        }

        getListSongs()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(listSongBroadcastReceiver)
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, getString(R.string.request_permission), Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        }
    }

    // Fetch musics
    private fun getListSongs() {
        songRepository.getSongsLocal(this.contentResolver, object : OnResultListener<MutableList<Song>> {
            override fun onSuccess(data: MutableList<Song>) {
                listSongs.addAll(data)
                if (listSongs.isEmpty()) {
                    binding.tvListSong.visibility = View.GONE
                    binding.tvNoSongs.visibility = View.VISIBLE
                } else {
                    songAdapter.setData(listSongs)
                    binding.recyclerViewListSongs.adapter = songAdapter
                }
            }

            override fun onError(exception: Exception?) {
                Toast.makeText(
                    this@SongMainActivity,
                    getString(R.string.error_loading_songs),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun startPlayMusic(song: Song) {
        val intent = Intent(this, SongService::class.java)
        val bundle = Bundle().apply {
            putParcelable(OBJECT_SONG_KEY, song)
            putBoolean(STATUS_PLAYER_KEY, true)
        }
        intent.putExtras(bundle)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    private fun showMusicPlayer(song: Song) {
        val position = listSongs.indexOf(song)

        val intent = Intent(this, MusicPlayerActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(OBJECT_SONG_KEY, song)
            putBoolean(STATUS_PLAYER_KEY, isPlaying)
            putInt(SONG_POSITION_KEY, position)
        }
        intent.putParcelableArrayListExtra(LIST_SONG_KEY, listSongs)
        intent.putExtras(bundle)
        musicPlayerActivityLauncher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        menu?.let { safeMenu ->
            val nightModeItem = safeMenu.findItem(R.id.night_mode)
            nightModeItem?.setTitle(
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    R.string.day_mode
                } else {
                    R.string.night_mode
                }
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.night_mode) {
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.root.setBackgroundResource(R.drawable.default_background)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.root.setBackgroundResource(R.drawable.background_img)
            }
            recreate()
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::song.isInitialized) {
            outState.putParcelable(SONG_INFO, song)
        }
    }

    companion object {
        const val ALBUM_ART = "content://media/external/audio/albumart"
    }
}
