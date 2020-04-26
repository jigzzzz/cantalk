package com.everyone.cantalk.ui.fragment.replyingmessage

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReplyingMessageBinding
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.ui.fragment.readingmessage.ReplyingMessageViewModel
import com.everyone.cantalk.util.MorseUtil

class ReplyingMessageFragment : BaseFragment<ReplyingMessageViewModel, FragmentReplyingMessageBinding>(ReplyingMessageViewModel::class.java, R.layout.fragment_replying_message), View.OnClickListener {

    private var message : String = ""
    private var userId : String = ""
    private lateinit var vibrator : Vibrator

    override fun setListener() {
        super.setListener()
        binding.buttonClear.setOnClickListener(this)
        binding.buttonUnderscored.setOnClickListener(this)
        binding.buttonRead.setOnClickListener(this)
        binding.buttonSend.setOnClickListener(this)
        binding.buttonSlice.setOnClickListener(this)
        binding.buttonSpace.setOnClickListener(this)
        binding.buttonDot.setOnClickListener(this)

        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        userId = arguments?.getString("friendId") ?: ""
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.button_clear -> {
                message = ""
                updateMessage()
            }
            R.id.button_underscored -> {
                message += '_'
                updateMessage()
            }
            R.id.button_send -> {
                if (userId.isBlank() || userId.isEmpty()) {
                    showError("You don't have any friend to reply")
                    vibrate(1000)
                }
                else {
                    viewModel.sendMessage(Chat(load().id, userId, binding.textMessage.text.toString()))
                    findNavController().navigate(R.id.action_replayingMessageFragment_to_readingMessageFragment)
                }
            }
            R.id.button_read -> {
                MorseUtil.readMorse(context!!, message)
            }
            R.id.button_slice -> {
                message += '/'
                vibrate(50)
                updateMessage()
            }
            R.id.button_space -> {
                message += ' '
                vibrate(50)
                updateMessage()
            }
            R.id.button_dot -> {
                message += '.'
                vibrate(50)
                updateMessage()
            }
        }
    }

    private fun updateMessage() {
        binding.textMessage.setText(MorseUtil.alphabetConverter(message))
    }

    private fun vibrate(millisecond: Long) {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    millisecond,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(millisecond)
        }
    }
}