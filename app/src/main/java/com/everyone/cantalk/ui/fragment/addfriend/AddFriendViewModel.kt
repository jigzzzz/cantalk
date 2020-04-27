package com.everyone.cantalk.ui.fragment.addfriend

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.UserRepository

class AddFriendViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getFriends(userId: String) : LiveData<List<User>> = userRepository.getFriends(userId)

}