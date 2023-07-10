package com.sun.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sun.android.databinding.ActivityDynamicFragmentBinding

class DynamicFragmentActivity : AppCompatActivity() {
    private val binding: ActivityDynamicFragmentBinding by lazy {
        ActivityDynamicFragmentBinding.inflate(layoutInflater)
    }

    private var isArticleFragmentVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnEnglish.setOnClickListener {
            replaceFragment(SpeciesCatEnglishFragment())
        }

        binding.btnJapanese.setOnClickListener {
            replaceFragment(SpeciesCatJapaneseFragment())
        }

        binding.btnVietnamese.setOnClickListener {
            replaceFragment(SpeciesCatVietnameseFragment())
        }

        binding.btnToggleArticleFragment.setOnClickListener {
            if (isArticleFragmentVisible) {
                hideArticleFragment()
                binding.btnToggleArticleFragment.text = getString(R.string.open)
            } else {
                showArticleFragment()
                binding.btnToggleArticleFragment.text = getString(R.string.close)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()
    }

    private fun showArticleFragment() {
        if (!isArticleFragmentVisible) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentArticle.id, ArticleFragment.newInstance())
                .commit()
            isArticleFragmentVisible = true
        }
    }

    private fun hideArticleFragment() {
        if (isArticleFragmentVisible) {
            val articleFragment = supportFragmentManager.findFragmentById(binding.fragmentArticle.id)
            articleFragment?.let {
                supportFragmentManager.beginTransaction().remove(articleFragment).commit()
                isArticleFragmentVisible = false
            }
        }
    }
}
