package com.everyone.cantalk.ui.message

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.everyone.cantalk.databinding.LayoutChatItemLeftBinding
import com.everyone.cantalk.databinding.LayoutChatItemRightBinding
import com.everyone.cantalk.model.Chat
import com.everyone.cantalk.util.SharedPrefUtil

class MessageAdapter(private val context: Context) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    companion object {
        private const val MSG_TYPE_RIGHT = 1
        private const val MSG_TYPE_LEFT  = 0
    }

    private var chats : List<Chat> = listOf()

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(chat: Chat)
    }

    inner class RightMessage(private val binding: LayoutChatItemRightBinding) : ViewHolder(binding.root) {
        override fun bind(chat: Chat) {
            binding.message = chat.message
        }
    }

    inner class LeftMessage(private val binding: LayoutChatItemLeftBinding) : ViewHolder(binding.root) {
        override fun bind(chat: Chat) {
            binding.message = chat.message
        }
    }

    fun setChats(chats: List<Chat>) {
        this.chats = chats
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val user = SharedPrefUtil(context).load()
        return when(user.id) {
            chats[position].sender -> MSG_TYPE_RIGHT
            else -> MSG_TYPE_LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            MSG_TYPE_RIGHT -> { RightMessage(LayoutChatItemRightBinding.inflate(LayoutInflater.from(parent.context)))}
            else -> { LeftMessage(LayoutChatItemLeftBinding.inflate(LayoutInflater.from(parent.context))) }
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chats[position])
    }

}