package com.everyone.cantalk.model

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
    }

}