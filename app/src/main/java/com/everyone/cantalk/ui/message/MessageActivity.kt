package com.everyone.cantalk.ui.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityMessageBinding
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.model.User
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

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setListener() {
        super.setListener()

        val messageAdapter = MessageAdapter(this)
        viewModel.getCurrentUser(intent?.getStringExtra(EXTRA_USER_ID)?:"").observe(this, Observer {
            user = it
            binding.name = user.name
            viewModel.readMessage(load().id, user.id).observe(this, Observer {chats ->
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
            if(binding.textMessage.text.toString().isNotBlank() || binding.textMessage.text.toString().isNotEmpty()){
                var message = binding.textMessage.text.toString().replace(".", "")
                message = message.replace("_", " ")
                viewModel.sendMessage(Chat(load().id, user.id, message))
                binding.textMessage.setText("")
            }
        }

    }
}