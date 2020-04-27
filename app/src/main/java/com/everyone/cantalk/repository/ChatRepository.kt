package com.everyone.cantalk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everyone.cantalk.model.Chat
import com.google.firebase.database.*

class ChatRepository {

    companion object {
        @Volatile
        var INSTANCE: ChatRepository? = null

        fun getInstance() : ChatRepository {
            if (INSTANCE == null) {
                synchronized(ChatRepository::class.java) {
                    if (INSTANCE == null)
                        INSTANCE =
                            ChatRepository()
                }
            }
            return INSTANCE!!
        }
    }

    private val database: DatabaseReference by lazy {FirebaseDatabase.getInstance().getReference("Chats")}

    fun sendMessage(chat: Chat) {
        database.push().setValue(Chat.hasMessage(chat))
    }

    fun readMessage(sender: String, receiver: String) : LiveData<List<Chat>> {
        val liveChats: MutableLiveData<List<Chat>> = MutableLiveData()
        val chats: MutableList<Chat> = mutableListOf()

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chats.clear()
                p0.children.forEach{
                    val chat : Chat = it.getValue(Chat::class.java)!!
                    if (chat.receiver.equals(receiver) && chat.sender.equals(sender) || chat.receiver.equals(sender) && chat.sender.equals(receiver))
                        chats.add(chat)
                }
                liveChats.postValue(chats)
            }
        })
        return liveChats
    }

    fun readMessage(success : (p0: DataSnapshot) -> Unit) {

        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                success(p0)
            }
        })

    }

}