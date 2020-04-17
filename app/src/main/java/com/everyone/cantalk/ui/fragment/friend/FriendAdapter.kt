package com.everyone.cantalk.ui.fragment.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everyone.cantalk.databinding.LayoutFriendItemBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.util.SharedPrefUtil

class FriendAdapter : RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    private lateinit var sharedPrefUtil: SharedPrefUtil
    private var friendList : List<User> = listOf()

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(user: User)
    }

    inner class DisabledViewHolder(private val binding: LayoutFriendItemBinding) : ViewHolder(binding.root) {
        override fun bind(user: User) {
            binding.name = user.name
            Log.d("<DEBUG>", "Friend Name " + user.name)
        }
    }

    inner class DefaultViewHolder(private val binding: LayoutFriendItemBinding) : ViewHolder(binding.root) {
        override fun bind(user: User) {
            binding.name = user.name
            Log.d("<DEBUG>", "Friend Name " + user.name)
        }
    }

    fun setFriendList(pFriendList: List<User>) {
        friendList = pFriendList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        sharedPrefUtil = SharedPrefUtil(parent.context)
        val user = sharedPrefUtil.load()

        return when (user.disabled) {
            true -> {
                DisabledViewHolder(LayoutFriendItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
            false -> {
                DefaultViewHolder(LayoutFriendItemBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    override fun getItemCount(): Int = friendList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(friendList[position])
    }

}