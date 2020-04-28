package com.everyone.cantalk.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.model.User
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

    private val dbRefChat : DatabaseReference by lazy {FirebaseDatabase.getInstance().getReference("Chats")}
    private val dbRefUser  : DatabaseReference by lazy { FirebaseDatabase.getInstance().getReference("Users") }

    fun sendMessage(chat: Chat) {
        dbRefChat.push().setValue(Chat.hasMessage(chat))
    }

    fun readMessage(success : (p0: DataSnapshot) -> Unit) {

        dbRefChat.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                success(p0)
            }
        })

    }

    fun getListFriendChats(sender: String) : LiveData<List<User>> {
        val chatFriendsLiveData : MutableLiveData<List<User>> = MutableLiveData()
        val chatFriends : MutableList<User> = mutableListOf()
        val userList : MutableList<String> = mutableListOf()

        dbRefChat.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                userList.clear()

                p0.children.forEach{
                    val chat = it.getValue(Chat::class.java)

                    if (chat?.sender.equals(sender)) {
                        userList.add(chat?.receiver ?: "")
                    }
                    if (chat?.receiver.equals(sender)) {
                        userList.add(chat?.sender ?: "")
                    }
                }

                dbRefUser.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        chatFriends.clear()
                        p0.children.forEach {
                            val user = it.getValue(User::class.java)
                            user?.id = it.key ?: ""

                            for (i in userList) {
                                if (user?.id.equals(i)) {
                                    if (chatFriends.size != 0) {
                                        for (friend in chatFriends) {
                                            if (!user?.id.equals(friend.id)) {
                                                chatFriends.add(user ?: User())
                                            }
                                        }
                                    }
                                    else {
                                        chatFriends.add(user ?: User())
                                    }
                                }
                            }
                        }
                        chatFriendsLiveData.postValue(chatFriends)
                    }
                })
            }
        })

        return chatFriendsLiveData
    }

    fun getListFriendChatsMessage(sender: String, userList: List<User>) : LiveData<List<String>> {
        val chatFriendsLiveData : MutableLiveData<List<String>> = MutableLiveData()
        val chatFriends : MutableList<String> = mutableListOf()

        dbRefChat.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                chatFriends.clear()

                for (i in userList) {
                    var message = ""
                    p0.children.forEach {
                        val chat = it.getValue(Chat::class.java) ?: Chat()
                        if (chat.sender.equals(sender) && chat.receiver.equals(i.id) || chat.receiver.equals(sender) && chat.sender.equals(i.id)){
                            message = chat.message
                        }
                    }
                    chatFriends.add(message)
                }

                chatFriendsLiveData.postValue(chatFriends)
            }
        })

        return chatFriendsLiveData
    }

//    fun getListFriendChatsMessage(sender: String) : LiveData<List<String>> {
//        val chatFriendsLiveData : MutableLiveData<List<String>> = MutableLiveData()
//        val chatFriends : MutableList<String> = mutableListOf()
//        val userList : MutableList<String> = mutableListOf()
//
//        dbRefChat.addValueEventListener(object : ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                userList.clear()
//                chatFriends.clear()
//
//                p0.children.forEach{
//                    val chat = it.getValue(Chat::class.java)
//
//                    if (chat?.sender.equals(sender) ) {
//                        userList.add(chat?.receiver ?: "")
//                    }
//                    if (chat?.receiver.equals(sender)) {
//                        userList.add(chat?.sender ?: "")
//                    }
//                }
//
//                for (i in userList) {
//                    val size = userList.size
//                    var message = ""
//                    p0.children.forEach {
//                        val chat = it.getValue(Chat::class.java)
//                        if (chat?.sender.equals(sender) && chat?.receiver.equals(i) || chat?.receiver.equals(sender) && chat?.sender.equals(i))
//                            message = chat?.message ?: ""
//                    }
//                    chatFriends.add(message)
//                }
//
//                chatFriendsLiveData.postValue(chatFriends)
//            }
//        })
//
//        return chatFriendsLiveData
//    }

}