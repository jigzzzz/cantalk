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

    private var friendList : List<User> = listOf()
    private var setOnItemClickedListener : SetOnItemClickedListener? = null

    interface SetOnItemClickedListener {
        fun onClick(user: User)
    }

    fun onClickedListener(setOnItemClickedListener: SetOnItemClickedListener) {
        this.setOnItemClickedListener = setOnItemClickedListener
    }

    inner class ViewHolder(private val binding: LayoutFriendItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.name = user.name
            binding.item.setOnClickListener {
                setOnItemClickedListener?.onClick(user)
            }
        }
    }

    fun setFriendList(pFriendList: List<User>) {
        friendList = pFriendList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutFriendItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = friendList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(friendList[position])
    }

}