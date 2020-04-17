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
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentFriendBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.addfriend.AddFriendActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FriendFragment : BaseFragment<FriendViewModel, FragmentFriendBinding>(FriendViewModel::class.java, R.layout.fragment_friend) {

    private val friendMutableList = mutableListOf<User>()
    private val user by lazy { load() }

    private fun setAdapter() {
        val friendAdapter = FriendAdapter()
        friendAdapter.setFriendList(friendMutableList)
        binding.rvFriend.apply {
            adapter = friendAdapter
            layoutManager = when (user.disabled) {
                true -> GridLayoutManager(context, 2)
                false -> LinearLayoutManager(context)
            }
        }
    }

    override fun setListener() {
        super.setListener()
        val dbRef = FirebaseDatabase.getInstance().getReference("Users")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                friendMutableList.clear()
                p0.children.forEach{
                    val firebaseUser = it.getValue(User::class.java)
                    if(firebaseUser != null) {
                        if (user.id != firebaseUser.id)
                            friendMutableList.add(firebaseUser)
                    }
                }

                setAdapter()
            }
        })

        binding.fabAddFriend.setOnClickListener {
            startActivity(Intent(context, AddFriendActivity::class.java))
        }
    }

}