package com.everyone.cantalk.ui.chat.guardian

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.remote.UserRepository

class ChatViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getCurrentUser(userId: String) : LiveData<User> {
        return userRepository.getCurrentUserData(userId)
    }

    fun getFriends(userId: String) : LiveData<List<User>> {
        return userRepository.getFriends(userId)
    }

    fun addFriend(userId: String, user: User, successListener: () -> Unit, failedListener : () -> Unit) {
        userRepository.addFriend(userId, user, successListener, failedListener)
    }

}