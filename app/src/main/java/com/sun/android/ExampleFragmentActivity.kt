package com.sun.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sun.android.databinding.ActivityExampleFragmentBinding

class ExampleFragmentActivity : AppCompatActivity() {
    private val binding: ActivityExampleFragmentBinding by lazy {
        ActivityExampleFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnEnglish.setOnClickListener {
            replaceFragment(EnglishFragment())
        }

        binding.btnJapanese.setOnClickListener {
            replaceFragment(JapaneseFragment())
        }

        binding.btnVietnamese.setOnClickListener {
            replaceFragment(VietnameseFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()
    }
}
