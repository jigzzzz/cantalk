package com.everyone.cantalk.ui.fragment.friend

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.remote.UserRepository

class FriendViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFriends(userId: String) : LiveData<List<User>> {
        return userRepository.getFriends(userId)
    }

}