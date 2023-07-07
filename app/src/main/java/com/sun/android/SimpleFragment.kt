package com.sun.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.android.databinding.FragmentSimpleBinding

class SimpleFragment : Fragment() {
    private val YES = 0
    private val NO = 1

    private val binding: FragmentSimpleBinding by lazy {
        FragmentSimpleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = binding.root

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
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

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = SimpleFragment()
    }
}
