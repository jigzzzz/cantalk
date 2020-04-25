package com.everyone.cantalk.model

import android.util.Log

data class User (var id: String = "",
                 val name: String = "",
                 val disabled: Boolean = false) {


    companion object {
        fun hashUser(user: User) : Map<String, Any?> {
            return mapOf(
                "name" to user.name,
                "disabled" to user.disabled
            )
        }

        fun checkFriend(users: List<User>, userId: String) : Boolean {
            val user: User? = users.find { it.id == userId }
            return when(user?.id) {
                null -> true
                "" -> true
                else -> false
            }
        }
    }

}