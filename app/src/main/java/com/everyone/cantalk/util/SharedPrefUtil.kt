package com.everyone.cantalk.util

import android.content.Context
import android.content.SharedPreferences
import com.everyone.cantalk.R
import com.everyone.cantalk.model.User

class SharedPrefUtil(private val context : Context) {

    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("Cantalk", Context.MODE_PRIVATE)

    fun isLoggedIn() : Boolean{
        return sharedPreferences.getBoolean(context.resources.getString(R.string.sp_is_login), false)
    }

    fun save(user : User) {
        val editor = sharedPreferences.edit()
        editor.putString(
            context.resources.getString(R.string.sp_id),
            user.id)

        editor.putString(
            context.resources.getString(R.string.sp_name),
            user.name)

        editor.putString(
            context.resources.getString(R.string.sp_name),
            user.name)

        editor.putBoolean(
            context.resources.getString(R.string.sp_is_disabled),
            user.disabled)

        editor.putBoolean(
            context.resources.getString(R.string.sp_is_login),
            true)

        editor.apply()
    }

    fun load() : User {
        return User(
            sharedPreferences.getString(context.resources.getString(R.string.sp_id), "")!!,
            sharedPreferences.getString(context.resources.getString(R.string.sp_name), "")!!,
            sharedPreferences.getBoolean(context.resources.getString(R.string.sp_is_disabled), false)
        )
    }

    fun logout() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}