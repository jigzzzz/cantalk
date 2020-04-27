package com.everyone.cantalk.di

import com.everyone.cantalk.repository.ChatRepository
import com.everyone.cantalk.repository.UserRepository

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