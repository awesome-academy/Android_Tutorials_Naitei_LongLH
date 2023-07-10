package com.sun.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.android.databinding.FragmentArticleBinding

private const val YES = 0
private const val NO = 1

class ArticleFragment : Fragment() {

    private val binding: FragmentArticleBinding by lazy {
        FragmentArticleBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = binding.radioGroup.findViewById<View>(checkedId)
            val index = binding.radioGroup.indexOfChild(radioButton)
            val textView = binding.fragmentHeader
            when (index) {
                YES -> textView.setText(R.string.yes_message)
                NO -> textView.setText(R.string.no_message)
                else -> {
                    // No choice made.
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleFragment()
    }
}
