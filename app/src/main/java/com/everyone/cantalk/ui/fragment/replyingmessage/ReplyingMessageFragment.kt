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
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReplyingMessageBinding
import com.everyone.cantalk.model.*
import com.everyone.cantalk.repository.remote.Client
import com.everyone.cantalk.repository.remote.RetrofitService
import com.everyone.cantalk.ui.fragment.readingmessage.ReplyingMessageViewModel
import com.everyone.cantalk.util.MorseUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback

class ReplyingMessageFragment : BaseFragment<ReplyingMessageViewModel, FragmentReplyingMessageBinding>(ReplyingMessageViewModel::class.java, R.layout.fragment_replying_message), View.OnClickListener {

    private var message : String = ""
    private var userId : String = ""
    private lateinit var vibrator : Vibrator
    private lateinit var service : RetrofitService

    override fun setListener() {
        super.setListener()
        binding.buttonClear.setOnClickListener(this)
        binding.buttonUnderscored.setOnClickListener(this)
        binding.buttonRead.setOnClickListener(this)
        binding.buttonSend.setOnClickListener(this)
        binding.buttonSlice.setOnClickListener(this)
        binding.buttonSpace.setOnClickListener(this)
        binding.buttonDot.setOnClickListener(this)

        service = Client.service
        vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        userId = arguments?.getString("friendId") ?: ""
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.button_clear -> {
                message = ""
                vibrate(50)
                binding.textMessage.setText("")
            }
            R.id.button_underscored -> {
                message += '_'
                vibrate(50)
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
                if (message.isNotEmpty() || message.isNotBlank()) {
                    MorseUtil.readMorse(context!!, message)
                }
                else
                    vibrate(50)
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

    private fun sendNotification(receiver: String, name: String, message: String) {
        val tokens = FirebaseDatabase.getInstance().getReference("Tokens")
        val query = tokens.orderByKey().equalTo(receiver)
        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val token : Token = it.getValue(Token::class.java) ?: Token()
                    val data : Notification = Notification(load().id, R.drawable.ic_notifications, message, name, userId)
                    val sender = Sender(data, token.token)
                    service.sendNotification(sender).enqueue(object : Callback<Response> {
                        override fun onFailure(call: Call<Response>, t: Throwable) {

                        }

                        override fun onResponse(
                            call: Call<Response>,
                            response: retrofit2.Response<Response>
                        ) {
                            when(response.isSuccessful) {
                                true -> {
                                    if (response.body()?.success != 1)
                                        Toast.makeText(context, "Failed!!", Toast.LENGTH_SHORT).show()
                                }
                                false -> {}
                            }
                        }
                    })
                }
            }
        })
    }

    private fun messageRead() {
        binding.buttonRead.isEnabled = true
        binding.buttonRead.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_disabled, null)
    }

    private fun messageReadyToRead() {
        binding.buttonRead.isEnabled = false
        binding.buttonRead.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_default, null)
    }
}