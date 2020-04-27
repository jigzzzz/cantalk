package com.everyone.cantalk.ui.fragment.readingmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.ChatRepository
import com.everyone.cantalk.repository.UserRepository

class ReadingMessageViewModel(private val userRepository: UserRepository, private val chatRepository: ChatRepository) : ViewModel() {

    fun getFriends(userId: String) : LiveData<List<User>> = userRepository.getFriends(userId)

    fun readMessage(sender: String, receiver: String) : LiveData<String> {
        val liveChats: MutableLiveData<String> = MutableLiveData()
        val chats: MutableList<Chat> = mutableListOf()

        chatRepository.readMessage{
            chats.clear()
            it.children.forEach{dataSnapshot ->
                val chat : Chat = dataSnapshot.getValue(Chat::class.java)!!
                if (chat.receiver.equals(receiver) && chat.sender.equals(sender) || chat.receiver.equals(sender) && chat.sender.equals(receiver))
                    chats.add(chat)
            }
            if (chats[chats.size-1].sender.equals(receiver))
                liveChats.postValue(chats[chats.lastIndex].message)
            else
                liveChats.postValue("")
        }

        return liveChats
    }

}