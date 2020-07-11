package com.everyone.cantalk.ui.fragment.readingmessage

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReadingMessageBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.util.MorseUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class ReadingMessageFragment : BaseFragment<ReadingMessageViewModel, FragmentReadingMessageBinding>(ReadingMessageViewModel::class.java, R.layout.fragment_reading_message) {

    private var user : User = User()
    private var message : MutableLiveData<String> = MutableLiveData()

    override fun setListener() {
        super.setListener()

        binding.textInformation.setOnClickListener {
            val text = MorseUtil.morseCodeConverter(binding.textInformation.text.toString())
            MorseUtil.readMorse(context!!, text)
        }

        binding.textNoMessage.setOnClickListener {
            val text = MorseUtil.morseCodeConverter(binding.textNoMessage.text.toString())
            MorseUtil.readMorse(context!!, text)
        }

        viewModel.getFriends(load().id).observe(this, Observer {
            user = it[0]
            binding.name = it[0].name
            viewModel.readMessage(load().id, user.id).observe(this, Observer { message ->
                when(message.isEmpty() || message.isBlank()) {
                    true -> {
                        displayNoMessage()
                    }
                    false -> {
                        this.message.postValue(message)
                        displayMessage()
                    }
                }
            })
        })

        binding.buttonReply.setOnClickListener {
            val bundle = bundleOf("friendId" to user.id)
            findNavController().navigate(R.id.action_readingMessageFragment_to_replayingMessageFragment, bundle)
        }

        binding.buttonRead.setOnClickListener {
            message.observe(this, Observer {
                val res = MorseUtil.morseCodeConverter(it)
                MorseUtil.readMorse(context!!, res)
            })
        }
    }

    private fun displayMessage() {
        binding.textInformation.visibility = View.VISIBLE
        binding.buttonRead.visibility = View.VISIBLE
        binding.textNoMessage.visibility = View.GONE
    }

    private fun displayNoMessage() {
        binding.textNoMessage.visibility = View.VISIBLE
        binding.textInformation.visibility = View.GONE
        binding.buttonRead.visibility = View.GONE
    }

    private fun messageRead() {
        binding.buttonRead.isEnabled = false
        binding.buttonRead.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_circle_disabled, null)
    }

    private fun messageReadyToRead() {
        binding.buttonRead.isEnabled = true
        binding.buttonRead.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_circle, null)
    }

}