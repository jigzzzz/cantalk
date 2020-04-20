package com.everyone.cantalk.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.remote.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getCurrentUser(userId: String) : LiveData<User> {
        return userRepository.getCurrentUserData(userId)
    }

}