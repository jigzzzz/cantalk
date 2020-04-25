package com.everyone.cantalk.di

import com.everyone.cantalk.repository.remote.ChatRepository
import com.everyone.cantalk.repository.remote.UserRepository

class Injection {
    companion object {
        fun provideUserRepository() : UserRepository {
            return UserRepository.getInstance()
        }
        fun provideChatRepository() : ChatRepository {
            return ChatRepository.getInstance()
        }
    }
}