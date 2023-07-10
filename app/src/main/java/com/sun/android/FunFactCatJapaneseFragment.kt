package com.sun.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.android.databinding.FragmentFunFactCatJapaneseBinding

class FunFactCatJapaneseFragment : Fragment() {

    private val binding: FragmentFunFactCatJapaneseBinding by lazy {
        FragmentFunFactCatJapaneseBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FunFactCatJapaneseFragment()
    }
}
