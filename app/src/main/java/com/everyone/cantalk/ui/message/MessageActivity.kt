package com.everyone.cantalk.ui.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityMessageBinding
import com.everyone.cantalk.model.*
import com.everyone.cantalk.repository.remote.Client
import com.everyone.cantalk.repository.remote.RetrofitService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageActivity : BaseActivity<MessageViewModel, ActivityMessageBinding>(MessageViewModel::class.java, R.layout.activity_message) {

    companion object {
        private const val EXTRA_USER_ID = "extra_user_id"
        fun getIntent(ctx: Context, userId: String) : Intent {
            val intent = Intent(ctx, MessageActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }

    private var friend = User()
    private lateinit var service : RetrofitService
    private var notify = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setListener() {
        super.setListener()

        service = Client.service

        val messageAdapter = MessageAdapter(this)
        viewModel.getCurrentUser(intent?.getStringExtra(EXTRA_USER_ID)?:"").observe(this, Observer {
            friend = it
            binding.name = friend.name
            viewModel.readMessage(load().id, friend.id).observe(this, Observer {chats ->
                messageAdapter.setChats(chats)
            })
        })

        val linearLayoutManager = LinearLayoutManager(this@MessageActivity)
        linearLayoutManager.stackFromEnd = true
        binding.rvChat.apply {
            setHasFixedSize(true)
            adapter = messageAdapter
            layoutManager = linearLayoutManager
        }

        binding.buttonSend.setOnClickListener {
            notify = true
            if(binding.textMessage.text.toString().isNotBlank() || binding.textMessage.text.toString().isNotEmpty()){
                var message = binding.textMessage.text.toString().replace(".", "")
                message = message.replace("_", " ")
                viewModel.sendMessage(Chat(load().id, friend.id, message))
                binding.textMessage.setText("")

                if (notify)
                    sendNotification(friend.id, load().name, message)
                notify = false
            }
        }
    }

    private fun sendNotification(receiver: String, name: String, message: String) {
        val tokens = FirebaseDatabase.getInstance().getReference("Tokens")
        val query = tokens.orderByKey().equalTo(receiver)
        query.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val token : Token = it.getValue(Token::class.java) ?: Token()
                    val data : Notification = Notification(load().id, R.drawable.ic_notifications, message, name, friend.id)
                    val sender = Sender(data, token.token)
                    val response = service.sendNotification(sender).execute()
                    when(response.isSuccessful) {
                        true -> {
                            if (response.body()?.success != 1)
                                Toast.makeText(this@MessageActivity, "Failed!!", Toast.LENGTH_SHORT).show()
                        }
                        false -> {}
                    }
                }
            }
        })
    }
}