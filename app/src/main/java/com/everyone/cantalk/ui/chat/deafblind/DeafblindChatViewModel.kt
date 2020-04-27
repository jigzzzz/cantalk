package com.everyone.cantalk.ui.chat.deafblind

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.UserRepository

class DeafblindChatViewModel(private val userRepository: UserRepository)  : ViewModel() {

    fun getFriends(userId: String) : LiveData<List<User>> {
        return userRepository.getFriends(userId)
    }

}