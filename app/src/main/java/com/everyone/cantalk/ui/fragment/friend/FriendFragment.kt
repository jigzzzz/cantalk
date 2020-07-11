package com.everyone.cantalk.ui.fragment.friend

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentFriendBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.addfriend.scanning.AddFriendActivity
import com.everyone.cantalk.ui.chat.guardian.ChatActivity
import com.everyone.cantalk.ui.message.MessageActivity

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