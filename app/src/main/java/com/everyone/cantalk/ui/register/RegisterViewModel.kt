package com.everyone.cantalk.ui.register

import androidx.lifecycle.ViewModel
import com.everyone.cantalk.repository.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun firebaseSignUpWithEmail(name: String, isDisabled: Boolean, email: String, password: String, successListener: (userId: String) -> Unit, successDelete: () -> Unit, failedListener: () -> Unit) {
        userRepository.firebaseSignUpWithEmail(name, isDisabled, email, password, successListener, successDelete, failedListener)
    }

}