package com.sun.android

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sun.android.databinding.ActivityImplicitIntentBinding

class ImplicitIntentActivity : AppCompatActivity() {
    private val binding: ActivityImplicitIntentBinding by lazy {
        ActivityImplicitIntentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = binding.root
        setContentView(view)

        binding.btnOpenWebsite.setOnClickListener {
            val linkWebsite = binding.etWebsite.text.toString()

            if (linkWebsite != "") {
                val uri = Uri.parse(linkWebsite)
                val openWebsiteIntent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(openWebsiteIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        this@ImplicitIntentActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@ImplicitIntentActivity,
                    getString(R.string.please_enter_website),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnClearInputWebsite.setOnClickListener {
            binding.etWebsite.text.clear()
        }

        binding.btnOpenLocation.setOnClickListener {
            val location = binding.etLocation.text.toString()

            if (location != "") {
                val uri = Uri.parse("geo:0,0?q=$location")
                val openLocationIntent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(openLocationIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        this@ImplicitIntentActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@ImplicitIntentActivity,
                    getString(R.string.please_enter_location),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnClearInputLocation.setOnClickListener {
            binding.etLocation.text.clear()
        }

        binding.btnSendMessage.setOnClickListener {
            val message = binding.etTextMessaging.text.toString()

            if (message != "") {
                val sendMessageIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                try {
                    startActivity(sendMessageIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        this@ImplicitIntentActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@ImplicitIntentActivity,
                    getString(R.string.please_enter_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnClearInputMessage.setOnClickListener {
            binding.etTextMessaging.text.clear()
        }
    }
}
