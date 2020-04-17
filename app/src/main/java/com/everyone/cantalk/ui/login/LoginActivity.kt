package com.everyone.cantalk.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.everyone.cantalk.ui.chat.guardian.ChatActivity
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityLoginBinding
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>(LoginViewModel::class.java, R.layout.activity_login) {

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {
            login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun login(email: String, password: String){
        binding.btnSignIn.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_disabled, null)
        binding.btnSignIn.isEnabled = false
        binding.btnSignIn.text = resources.getString(R.string.loading)

        viewModel.firebaseSignInWithEmail(email, password, {
            firebaseSignInSuccessful()
        }, {
            firebaseSignInFailed()
        })
    }

    private fun firebaseSignInSuccessful() {
        val firebaseUser = firebaseAuth.currentUser
        val userId = firebaseUser!!.uid
        viewModel.getCurrentUser(userId).observe(this, Observer {
            save(it)
            when(it.disabled) {
                true -> {
                    startActivity(
                        DeafblindChatActivity.getIntent(
                            this,
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    )
                    finish()
                }
                false -> {
                    startActivity(
                        ChatActivity.getIntent(
                            this,
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    )
                    finish()
                }
            }
        })
//        save(viewModel.getUser())
//        when(viewModel.getUser().disabled) {
//            true -> {
//                startActivity(
//                    DeafblindChatActivity.getIntent(
//                        this,
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                    )
//                )
//                finish()
//            }
//            false -> {
//                startActivity(
//                    ChatActivity.getIntent(
//                        this,
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                    )
//                )
//                finish()
//            }
//        }
    }

    private fun firebaseSignInFailed() {
        showError("Oops! Login is failed", "We are sorry, please try again")
        binding.btnSignIn.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_default, null)
        binding.btnSignIn.isEnabled = true
        binding.btnSignIn.text = resources.getString(R.string.sign_up)
    }
}
