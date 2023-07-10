package com.sun.android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.android.databinding.FragmentSpeciesCatVietnameseBinding
import com.sun.android.constants.*

class SpeciesCatVietnameseFragment : Fragment() {

    private val binding: FragmentSpeciesCatVietnameseBinding by lazy {
        FragmentSpeciesCatVietnameseBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.btnViewDetailCat1.setOnClickListener {
            val intent = Intent(activity, CatDetailActivity::class.java)
            intent.putExtra(CAT_INDEX_KEY, CAT_1_INDEX)
            intent.putExtra(LANGUAGE_KEY, VIETNAMESE_LANGUAGE)
            startActivity(intent)
        }

        binding.btnViewDetailCat2.setOnClickListener {
            val intent = Intent(activity, CatDetailActivity::class.java)
            intent.putExtra(CAT_INDEX_KEY, CAT_2_INDEX)
            intent.putExtra(LANGUAGE_KEY, VIETNAMESE_LANGUAGE)
            startActivity(intent)
        }

        binding.btnViewDetailCat3.setOnClickListener {
            val intent = Intent(activity, CatDetailActivity::class.java)
            intent.putExtra(CAT_INDEX_KEY, CAT_3_INDEX)
            intent.putExtra(LANGUAGE_KEY, VIETNAMESE_LANGUAGE)
            startActivity(intent)
        }

        binding.btnViewDetailCat4.setOnClickListener {
            val intent = Intent(activity, CatDetailActivity::class.java)
            intent.putExtra(CAT_INDEX_KEY, CAT_4_INDEX)
            intent.putExtra(LANGUAGE_KEY, VIETNAMESE_LANGUAGE)
            startActivity(intent)
        }

        binding.btnViewDetailCat5.setOnClickListener {
            val intent = Intent(activity, CatDetailActivity::class.java)
            intent.putExtra(CAT_INDEX_KEY, CAT_5_INDEX)
            intent.putExtra(LANGUAGE_KEY, VIETNAMESE_LANGUAGE)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SpeciesCatVietnameseFragment()
    }
}
