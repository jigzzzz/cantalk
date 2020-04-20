package com.everyone.cantalk.di

import com.everyone.cantalk.repository.remote.UserRepository

class Injection {
    companion object {
        fun provideAuthRepository() : UserRepository {
            return UserRepository.getInstance()
        }
    }
}