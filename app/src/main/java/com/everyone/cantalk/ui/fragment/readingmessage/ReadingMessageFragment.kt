package com.everyone.cantalk.ui.fragment.readingmessage

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReadingMessageBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.util.MorseUtil

class ReadingMessageFragment : BaseFragment<ReadingMessageViewModel, FragmentReadingMessageBinding>(ReadingMessageViewModel::class.java, R.layout.fragment_reading_message) {

    private var user : User = User()

    override fun setListener() {
        super.setListener()

        binding.textInformation.setOnClickListener {
            val text = MorseUtil.morseCodeConverter(binding.textInformation.text.toString())
            MorseUtil.readMorse(context!!, text)
        }

        viewModel.getFriends(load().id).observe(this, Observer {
            user = it[0]
            binding.name = it[0].name
            viewModel.readMessage(load().id, user.id).observe(this, Observer { message ->
                when(message.isEmpty() || message.isBlank()) {
                    true -> {

                    }
                    false -> {}
                }
            })
        })

        binding.buttonReply.setOnClickListener {
            findNavController().navigate(R.id.action_readingMessageFragment_to_replayingMessageFragment)
        }
    }

    fun displayMessage() {
        binding.textInformation.visibility = View.VISIBLE
        binding.buttonReply.visibility = View.VISIBLE
        binding.buttonRead.visibility = View.VISIBLE
    }

    fun displayNoMessage() {
        binding.textNoMessage.visibility = View.VISIBLE
    }
}