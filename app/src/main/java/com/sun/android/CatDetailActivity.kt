package com.sun.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sun.android.databinding.ActivityCatDetailBinding
import com.sun.android.constants.*

class CatDetailActivity : AppCompatActivity() {

    private val binding: ActivityCatDetailBinding by lazy {
        ActivityCatDetailBinding.inflate(layoutInflater)
    }

    private var catIndex = 0
    private var language = DEFAULT_LANGUAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        catIndex = intent.getIntExtra(CAT_INDEX_KEY, DEFAULT_CAT_INDEX)
        language = intent.getStringExtra(LANGUAGE_KEY).toString()

        if (catIndex != -1) {
            val fragment = CatDetailFragment.newInstance(catIndex = catIndex, language = language)
            supportFragmentManager.beginTransaction()
                .replace(R.id.cat_detail_fragment, fragment)
                .commit()
        }
    }
}
