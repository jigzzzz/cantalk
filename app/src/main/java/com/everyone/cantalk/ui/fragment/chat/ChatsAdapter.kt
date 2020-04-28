package com.everyone.cantalk.ui.fragment.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everyone.cantalk.databinding.LayoutChatItemBinding
import com.everyone.cantalk.databinding.LayoutFriendItemBinding
import com.everyone.cantalk.model.User

class ChatsAdapter : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    private var friendList : List<User> = listOf()
    private var messageList : List<String> = listOf()
    private var setOnItemClickedListener : SetOnItemClickedListener? = null

    interface SetOnItemClickedListener {
        fun onClick(user: User)
    }

    fun onClickedListener(setOnItemClickedListener: SetOnItemClickedListener) {
        this.setOnItemClickedListener = setOnItemClickedListener
    }

    inner class ViewHolder(private val binding: LayoutChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, message: String) {
            binding.name = user.name
            binding.message = message
            binding.item.setOnClickListener {
                setOnItemClickedListener?.onClick(user)
            }
        }
    }

    fun setFriendList(pFriendList: List<User>, pFriendMessage: List<String>) {
        friendList = pFriendList
        messageList = pFriendMessage
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutChatItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = friendList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(friendList[position], messageList[position])
    }

}