package com.everyone.cantalk.ui.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityMessageBinding
import com.everyone.cantalk.model.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setListener() {
        super.setListener()

        var lastMessage = false


        val messageAdapter = MessageAdapter(this)
        viewModel.getCurrentUser(intent?.getStringExtra(EXTRA_USER_ID)?:"").observe(this, Observer {
            friend = it
            binding.name = friend.name
            viewModel.readMessage(load().id, friend.id).observe(this, Observer {chats ->
                messageAdapter.setChats(chats)
            })
            viewModel.readLastMessage(load().id, friend.id).observe(this, Observer { message ->
                lastMessage = message.isNotBlank() || message.isNotEmpty()
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
            if(binding.textMessage.text.toString().isNotBlank() || binding.textMessage.text.toString().isNotEmpty()){
                when(lastMessage) {
                    true -> {
                        showConfirmation("Do you really wanna send another message?", "", "If you have another chat, your last message that will show up on your friend", R.drawable.disabled_v1){
                            var message = binding.textMessage.text.toString().replace(".", "")
                            message = message.replace("_", " ")
                            viewModel.sendMessage(Chat(load().id, friend.id, message))
                            binding.textMessage.setText("")

                        }
                    }
                    false -> {
                        var message = binding.textMessage.text.toString().replace(".", "")
                        message = message.replace("_", " ")
                        viewModel.sendMessage(Chat(load().id, friend.id, message))
                        binding.textMessage.setText("")

                    }
                }
            }
        }
    }

}