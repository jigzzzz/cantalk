package com.everyone.cantalk.ui.fragment.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentChatBinding
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.model.Token
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.message.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId

class ChatsFragment : BaseFragment<ChatsViewModel, FragmentChatBinding>(ChatsViewModel::class.java, R.layout.fragment_chat) {

    private val chatFriendsLiveData: MutableLiveData<List<User>> = MutableLiveData()
    private val chatFriends: MutableList<User> = mutableListOf()
    private val userList: MutableList<String> = mutableListOf()

    override fun setListener() {
        super.setListener()
        getChats()
        val chatAdapter = ChatsAdapter()
        chatAdapter.onClickedListener(object : ChatsAdapter.SetOnItemClickedListener{
            override fun onClick(user: User) {
                startActivity(MessageActivity.getIntent(context!!, user.id))
            }
        })
        chatFriendsLiveData.observe(this, Observer {
            chatAdapter.setFriendList(it)
        })
        binding.rvListChat.apply {
            setHasFixedSize(true)
            adapter = chatAdapter
            layoutManager = LinearLayoutManager(context)
        }

        updateToken(FirebaseInstanceId.getInstance().token ?: "")
    }

    private fun updateToken(refreshToken: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("Tokens")
        val token = Token(refreshToken)
        dbRef.child(user?.uid ?: "").setValue(token)
    }

    private fun getChats() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Chats")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                userList.clear()

                p0.children.forEach{
                    val chat = it.getValue(Chat::class.java)

                    if (chat?.sender.equals(load().id)) {
                        userList.add(chat?.receiver ?: "")
                    }
                    if (chat?.receiver.equals(load().id)) {
                        userList.add(chat?.sender ?: "")
                    }
                }
                readChats()
            }
        })
    }

    private fun readChats() {
        val dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener{
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

}