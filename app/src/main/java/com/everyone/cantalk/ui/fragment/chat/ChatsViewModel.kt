package com.everyone.cantalk.ui.fragment.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.ChatRepository

class ChatsViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    fun getListChat(sender : String) : LiveData<List<User>> = chatRepository.getListFriendChats(sender)
    fun getListChatMessage(sender: String, userList: List<User>) : LiveData<List<String>> = chatRepository.getListFriendChatsMessage(sender, userList)

}