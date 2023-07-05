package com.sun.android

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sun.android.databinding.ActivitySecondBinding

private const val MESSAGE_KEY = "msg"
private const val REPLY_KEY = "replyMsg"

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val sendMsg = intent.getStringExtra(MESSAGE_KEY)

        val inputField = binding.etReply
        val btnReply = binding.btnReply
        val textView = binding.msgContent
        textView.text = sendMsg

        btnReply.setOnClickListener {
            val reply = inputField.text.toString()
            if (reply == "") {
                Toast.makeText(
                    this@SecondActivity,
                    getString(R.string.placeholder_reply),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val replyIntent = Intent()
                replyIntent.putExtra(REPLY_KEY, reply)
                setResult(Activity.RESULT_OK, replyIntent)
                inputField.text.clear()
                finish()
            }
        }
    }
}
