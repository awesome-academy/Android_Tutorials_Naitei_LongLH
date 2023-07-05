package com.sun.android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.sun.android.databinding.ActivityMainBinding

private const val MESSAGE_KEY = "msg"
private const val REPLY_KEY = "replyMsg"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var msgContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val inputField = binding.etMessage
        val btnSend = binding.btnSend
        msgContent = binding.tvContent
        var enteredMessage: String

        btnSend.setOnClickListener {
            enteredMessage = inputField.text.toString()
            if (enteredMessage == "") {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.placeholder_message),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra(MESSAGE_KEY, enteredMessage)
                resultLauncher.launch(intent)
                inputField.text.clear()
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val reply = result.data?.getStringExtra(REPLY_KEY)
            msgContent.text = reply
        }
    }

}
