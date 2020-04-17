package com.everyone.cantalk.ui.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityMainBinding
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatActivity
import com.everyone.cantalk.ui.chat.guardian.ChatActivity
import com.everyone.cantalk.ui.login.LoginActivity
import com.everyone.cantalk.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java, R.layout.activity_main), View.OnClickListener {

    var firebaseUser: FirebaseUser? = null

    companion object {
        fun getIntent(ctx: Context): Intent {
            return Intent(ctx, MainActivity::class.java)
        }
    }

    override fun setListener() {
        super.setListener()
        binding.btnSignUp.setOnClickListener(this)
        binding.btnSignIn.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_sign_up ->
                startActivity(RegisterActivity.getIntent(this))
            R.id.btn_sign_in ->
                startActivity(LoginActivity.getIntent(this))
        }
    }
}
