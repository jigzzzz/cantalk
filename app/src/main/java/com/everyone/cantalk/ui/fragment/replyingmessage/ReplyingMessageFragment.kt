package com.everyone.cantalk.ui.fragment.replyingmessage

import android.view.View
import android.widget.Toast
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReplyingMessageBinding
import com.everyone.cantalk.ui.fragment.readingmessage.ReplyingMessageViewModel

class ReplyingMessageFragment : BaseFragment<ReplyingMessageViewModel, FragmentReplyingMessageBinding>(ReplyingMessageViewModel::class.java, R.layout.fragment_replying_message), View.OnClickListener {

    private var message : String = ""

    override fun setListener() {
        super.setListener()
        binding.buttonClear.setOnClickListener(this)
        binding.buttonDash.setOnClickListener(this)
        binding.buttonRead.setOnClickListener(this)
        binding.buttonSend.setOnClickListener(this)
        binding.buttonSlice.setOnClickListener(this)
        binding.buttonSpace.setOnClickListener(this)
        binding.buttonDot.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.button_clear -> {
                message = ""
                updateMessage()
            }
            R.id.button_dash -> {
                message += '-'
                updateMessage()
            }
            R.id.button_send -> {
                Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
            }
            R.id.button_read -> {
                Toast.makeText(context, "Message Read", Toast.LENGTH_SHORT).show()
            }
            R.id.button_slice -> {
                message += '/'
                updateMessage()
            }
            R.id.button_space -> {
                message += ' '
                updateMessage()
            }
            R.id.button_dot -> {
                message += '.'
                updateMessage()
            }
        }
    }

    private fun updateMessage() {
        binding.textMessage.setText(message)
    }
}