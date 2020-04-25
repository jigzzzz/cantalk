package com.everyone.cantalk.model

data class Chat(
    val sender: String = "",
    val receiver: String = "",
    val message: String = ""
) {

    companion object {
        fun hasMessage(chat: Chat) : Map<String, Any?> {
            return mapOf(
                "sender" to chat.sender,
                "receiver" to chat.receiver,
                "message" to chat.message
            )
        }
    }

}