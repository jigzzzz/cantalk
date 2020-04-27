package com.everyone.cantalk.ui.fragment.readingmessage

import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.repository.ChatRepository

class ReplyingMessageViewModel(private val chatRepository: ChatRepository) : ViewModel() {
    fun sendMessage(chat: Chat) = chatRepository.sendMessage(chat)
}