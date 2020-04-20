package com.everyone.cantalk.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everyone.cantalk.di.Injection
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatViewModel
import com.everyone.cantalk.ui.fragment.addfriend.AddFriendViewModel
import com.everyone.cantalk.ui.fragment.chat.ChatViewModel
import com.everyone.cantalk.ui.fragment.friend.FriendViewModel
import com.everyone.cantalk.ui.fragment.readingmessage.ReadingMessageViewModel
import com.everyone.cantalk.ui.fragment.readingmessage.ReplyingMessageViewModel
import com.everyone.cantalk.ui.login.LoginViewModel
import com.everyone.cantalk.ui.main.MainViewModel
import com.everyone.cantalk.ui.register.RegisterViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    companion object {
        @Volatile
        var INSTANCE: ViewModelFactory? = null

        fun getInstance() : ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(Injection.provideAuthRepository())
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(Injection.provideAuthRepository())
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(Injection.provideAuthRepository())
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> ChatViewModel()
            modelClass.isAssignableFrom(FriendViewModel::class.java) -> FriendViewModel(Injection.provideAuthRepository())
            modelClass.isAssignableFrom(DeafblindChatViewModel::class.java) -> DeafblindChatViewModel()
            modelClass.isAssignableFrom(AddFriendViewModel::class.java) -> AddFriendViewModel()
            modelClass.isAssignableFrom(ReadingMessageViewModel::class.java) -> ReadingMessageViewModel()
            modelClass.isAssignableFrom(ReplyingMessageViewModel::class.java) -> ReplyingMessageViewModel()
            else -> null
        } as T
    }

}