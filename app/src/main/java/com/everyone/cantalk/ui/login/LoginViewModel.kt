package com.everyone.cantalk.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.model.User
import com.everyone.cantalk.repository.remote.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel(){

    private var user : User = User("", "", false)

    fun getUser() : User {
        return user
    }

    fun firebaseSignInWithEmail(email: String, password: String, successListener: () -> Unit, failedListener: () -> Unit) {
        userRepository.firebaseSignInWithEmail(email, password,successListener, failedListener)
    }

    fun getCurrentUser(userId: String) : LiveData<User> {
        return userRepository.getCurrentUserData(userId)
    }

}