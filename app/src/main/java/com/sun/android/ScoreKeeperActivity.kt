package com.sun.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sun.android.databinding.ActivityScoreKeeperBinding

class ScoreKeeperActivity : AppCompatActivity() {

    private val binding: ActivityScoreKeeperBinding by lazy {
        ActivityScoreKeeperBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvScoreTeam1.text = DEFAULT_SCORE.toString()
        binding.tvScoreTeam2.text = DEFAULT_SCORE.toString()

        binding.btnMinusTeam1.setOnClickListener {
            decreaseScoreTeam(it)
        }

        binding.btnMinusTeam2.setOnClickListener {
            decreaseScoreTeam(it)
        }

        binding.btnPlusTeam1.setOnClickListener {
            increaseScoreTeam(it)
        }

        binding.btnPlusTeam2.setOnClickListener {
            increaseScoreTeam(it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        binding.tvScoreTeam1.text = savedInstanceState.getInt(STATE_SCORE_1).toString()
        binding.tvScoreTeam2.text = savedInstanceState.getInt(STATE_SCORE_2).toString()
    }

    private fun decreaseScoreTeam(view: View) {
        when (view.id) {
            R.id.btn_minus_team_1 -> {
                var score = binding.tvScoreTeam1.text.toString().toInt()
                score--
                binding.tvScoreTeam1.text = score.toString()
            }
            R.id.btn_minus_team_2 -> {
                var score = binding.tvScoreTeam2.text.toString().toInt()
                score--
                binding.tvScoreTeam2.text = score.toString()
            }
        }
    }

    private fun increaseScoreTeam(view: View) {
        when (view.id) {
            R.id.btn_plus_team_1 -> {
                var score = binding.tvScoreTeam1.text.toString().toInt()
                score++
                binding.tvScoreTeam1.text = score.toString()
            }
            R.id.btn_plus_team_2 -> {
                var score = binding.tvScoreTeam2.text.toString().toInt()
                score++
                binding.tvScoreTeam2.text = score.toString()
            }
        }
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
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            // Recreate the activity for the theme change to take effect.
            recreate()
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SCORE_1, binding.tvScoreTeam1.text.toString().toInt())
        outState.putInt(STATE_SCORE_2, binding.tvScoreTeam2.text.toString().toInt())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val STATE_SCORE_1 = "Team 1 Score"
        const val STATE_SCORE_2 = "Team 2 Score"
        const val DEFAULT_SCORE = 0
    }
}
