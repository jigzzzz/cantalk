package com.everyone.cantalk.model

data class Notification(
    val user: String,
    val icon: Int,
    val body: String,
    val title: String,
    val sented: String)