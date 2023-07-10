package com.sun.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.android.databinding.FragmentCatDetailBinding
import com.sun.android.constants.*

class CatDetailFragment : Fragment() {

    private lateinit var binding: FragmentCatDetailBinding
    private var catIndex = DEFAULT_CAT_INDEX
    private var language = DEFAULT_LANGUAGE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            catIndex = this.getInt(CAT_INDEX_KEY, DEFAULT_CAT_INDEX)
            language = this.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatDetailBinding.inflate(layoutInflater)

        if (catIndex != -1) {
            val imageCatResId: Int = when (catIndex) {
                CAT_1_INDEX -> R.drawable.meo_xiem
                CAT_2_INDEX -> R.drawable.meo_anh_long_dai
                CAT_3_INDEX -> R.drawable.meo_anh_long_ngan
                CAT_4_INDEX -> R.drawable.meo_tai_cup
                CAT_5_INDEX -> R.drawable.meo_muop
                else -> NOT_FOUND
            }
            val fact1ResId: Int = when (language) {
                VIETNAMESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "vietnamese_detail_fact_1_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                ENGLISH_LANGUAGE -> {
                    resources.getIdentifier(
                        "english_detail_fact_1_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                JAPANESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "japanese_detail_fact_1_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                else -> NOT_FOUND
            }
            val fact2ResId: Int = when (language) {
                VIETNAMESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "vietnamese_detail_fact_2_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                ENGLISH_LANGUAGE -> {
                    resources.getIdentifier(
                        "english_detail_fact_2_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                JAPANESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "japanese_detail_fact_2_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                else -> NOT_FOUND
            }
            val fact3ResId: Int = when (language) {
                VIETNAMESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "vietnamese_detail_fact_3_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                ENGLISH_LANGUAGE -> {
                    resources.getIdentifier(
                        "english_detail_fact_3_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                JAPANESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "japanese_detail_fact_3_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                else -> NOT_FOUND
            }
            val speciesResId: Int = when (language) {
                VIETNAMESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "vietnamese_title_species_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                ENGLISH_LANGUAGE -> {
                    resources.getIdentifier(
                        "english_title_species_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                JAPANESE_LANGUAGE -> {
                    resources.getIdentifier(
                        "japanese_title_species_cat_$catIndex",
                        "string",
                        requireContext().packageName
                    )
                }
                else -> NOT_FOUND
            }
            binding.tvSpeciesCatFact1.text = getString(fact1ResId)
            binding.tvSpeciesCatFact2.text = getString(fact2ResId)
            binding.tvSpeciesCatFact3.text = getString(fact3ResId)
            binding.tvSpeciesCat.text = getString(speciesResId)
            binding.imageDetailCat.setImageResource(imageCatResId)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(catIndex: Int, language: String) =
            CatDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(CAT_INDEX_KEY, catIndex)
                    putString(LANGUAGE_KEY, language)
                }
            }
    }
}
