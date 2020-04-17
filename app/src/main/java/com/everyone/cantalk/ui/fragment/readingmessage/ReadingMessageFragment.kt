package com.everyone.cantalk.ui.fragment.readingmessage

import androidx.navigation.fragment.findNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentReadingMessageBinding

class ReadingMessageFragment : BaseFragment<ReplyingMessageViewModel, FragmentReadingMessageBinding>(ReplyingMessageViewModel::class.java, R.layout.fragment_reading_message) {

    override fun setListener() {
        super.setListener()

        binding.buttonReply.setOnClickListener {
            findNavController().navigate(R.id.action_readingMessageFragment_to_replayingMessageFragment)
        }
    }
}