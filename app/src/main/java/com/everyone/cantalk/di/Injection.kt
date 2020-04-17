package com.everyone.cantalk.di

import com.everyone.cantalk.repository.remote.AuthRepository

class Injection {
    companion object {
        fun provideAuthRepository() : AuthRepository {
            return AuthRepository.getInstance()
        }
    }
}