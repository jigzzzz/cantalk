package com.everyone.cantalk.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.ChatRepository
import com.everyone.cantalk.repository.UserRepository

class MessageViewModel(private val userRepository: UserRepository, private val chatRepository: ChatRepository) : ViewModel() {

    fun getCurrentUser(userId: String) : LiveData<User> = userRepository.getCurrentUserData(userId)

    fun sendMessage(chat: Chat) = chatRepository.sendMessage(chat)

    fun readMessage(sender: String, receiver: String) : LiveData<List<Chat>> {

        val liveChats: MutableLiveData<List<Chat>> = MutableLiveData()
        val chats: MutableList<Chat> = mutableListOf()

        chatRepository.readMessage{
            chats.clear()
            it.children.forEach{dataSnapshot ->
                val chat : Chat = dataSnapshot.getValue(Chat::class.java)!!
                if (chat.receiver.equals(receiver) && chat.sender.equals(sender) || chat.receiver.equals(sender) && chat.sender.equals(receiver))
                    chats.add(chat)
            }
            liveChats.postValue(chats)
        }
        return liveChats
    }

}