package com.everyone.cantalk.service

import com.everyone.cantalk.model.Token
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseIdService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val user = FirebaseAuth.getInstance().currentUser
        val refreshToken = FirebaseInstanceId.getInstance().token
        if (user != null)
            updateToken(refreshToken ?: "")
    }

    private fun updateToken(refreshToken: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val dbRef = FirebaseDatabase.getInstance().getReference("Tokens")
        val token = Token(refreshToken)
        dbRef.child(user?.uid ?: "").setValue(token)
    }
}