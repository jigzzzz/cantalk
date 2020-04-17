package com.everyone.cantalk.ui.register

import androidx.lifecycle.ViewModel
import com.everyone.cantalk.repository.remote.AuthRepository

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun firebaseSignUpWithEmail(name: String, isDisabled: Boolean, email: String, password: String, successListener: (userId: String) -> Unit, successDelete: () -> Unit, failedListener: () -> Unit) {
        authRepository.firebaseSignUpWithEmail(name, isDisabled, email, password, successListener, successDelete, failedListener)
    }

}