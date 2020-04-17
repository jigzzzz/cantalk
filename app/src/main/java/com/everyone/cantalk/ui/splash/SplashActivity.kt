package com.everyone.cantalk.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModel
import com.everyone.cantalk.ui.main.MainActivity
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityDeafblindChatBinding
import com.everyone.cantalk.databinding.ActivitySplashBinding
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatActivity
import com.everyone.cantalk.ui.chat.guardian.ChatActivity

class SplashActivity : BaseActivity<ViewModel, ActivitySplashBinding>(ViewModel::class.java, R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            when(isLoggedIn()) {
                true -> {
                    when(load().disabled) {
                        true -> {
                            startActivity(
                                DeafblindChatActivity.getIntent(
                                    this
                                )
                            )
                            finish()
                        }
                        false -> {
                            startActivity(
                                ChatActivity.getIntent(
                                    this
                                )
                            )
                            finish()
                        }
                    }
                }
                false -> {
                    startActivity(
                        MainActivity.getIntent(
                            this
                        )
                    )
                    finish()
                }
            }
        }, 2500)

    }
}
