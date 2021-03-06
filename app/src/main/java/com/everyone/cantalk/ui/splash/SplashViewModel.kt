package com.everyone.cantalk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.UserRepository

class SplashViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFriends(userId: String) : LiveData<List<User>> {
        return userRepository.getFriends(userId)
    }

}