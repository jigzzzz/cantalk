package com.everyone.cantalk.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everyone.cantalk.di.Injection
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatViewModel
import com.everyone.cantalk.ui.chat.guardian.ChatViewModel
import com.everyone.cantalk.ui.fragment.addfriend.AddFriendViewModel
import com.everyone.cantalk.ui.fragment.chat.ListChatViewModel
import com.everyone.cantalk.ui.fragment.friend.FriendViewModel
import com.everyone.cantalk.ui.fragment.readingmessage.ReadingMessageViewModel
import com.everyone.cantalk.ui.fragment.readingmessage.ReplyingMessageViewModel
import com.everyone.cantalk.ui.login.LoginViewModel
import com.everyone.cantalk.ui.main.MainViewModel
import com.everyone.cantalk.ui.message.MessageViewModel
import com.everyone.cantalk.ui.register.RegisterViewModel
import com.everyone.cantalk.ui.splash.SplashViewModel

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
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(ListChatViewModel::class.java) -> ListChatViewModel()
            modelClass.isAssignableFrom(FriendViewModel::class.java) -> FriendViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(DeafblindChatViewModel::class.java) -> DeafblindChatViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(AddFriendViewModel::class.java) -> AddFriendViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(ReadingMessageViewModel::class.java) -> ReadingMessageViewModel(Injection.provideUserRepository(), Injection.provideChatRepository())
            modelClass.isAssignableFrom(ReplyingMessageViewModel::class.java) -> ReplyingMessageViewModel(Injection.provideChatRepository())
            modelClass.isAssignableFrom(ChatViewModel::class.java) -> ChatViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(Injection.provideUserRepository())
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> MessageViewModel(Injection.provideUserRepository(), Injection.provideChatRepository())
            else -> null
        } as T
    }

}