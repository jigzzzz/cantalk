package com.everyone.cantalk.ui.fragment.friend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentFriendBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.addfriend.AddFriendActivity
import com.everyone.cantalk.ui.chat.guardian.ChatActivity
import com.everyone.cantalk.ui.message.MessageActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendFragment : BaseFragment<FriendViewModel, FragmentFriendBinding>(FriendViewModel::class.java, R.layout.fragment_friend) {

    private val friendMutableList = mutableListOf<User>()

    private fun setAdapter() {
        val friendAdapter = FriendAdapter()
        friendAdapter.setFriendList(friendMutableList)
        friendAdapter.onClickedListener(object : FriendAdapter.SetOnItemClickedListener{
            override fun onClick(user: User) {
                startActivity(MessageActivity.getIntent(context!!, user.id))
            }
        })
        binding.rvFriend.apply {
            adapter = friendAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun setListener() {
        super.setListener()
        viewModel.getFriends(load().id).observe(this, Observer {
            friendMutableList.clear()
            friendMutableList.addAll(it)
            setAdapter()
        })

        binding.fabAddFriend.setOnClickListener {
            activity?.startActivityForResult(Intent(context, AddFriendActivity::class.java), ChatActivity.REQUEST_CODE)
        }
    }

}