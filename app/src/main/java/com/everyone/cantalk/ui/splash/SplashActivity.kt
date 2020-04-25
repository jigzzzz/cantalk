package com.everyone.cantalk.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import com.everyone.cantalk.ui.main.MainActivity
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivitySplashBinding
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatActivity
import com.everyone.cantalk.ui.chat.guardian.ChatActivity

class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>(SplashViewModel::class.java, R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Handler().postDelayed({
            when(isLoggedIn()) {
                true -> {
                    when(load().disabled) {
                        true -> {
                            viewModel.getFriends(load().id).observe(this, Observer {
                                when(it.size) {
                                    0 -> {
                                        Handler().postDelayed({
                                            startActivity(
                                                DeafblindChatActivity.getIntent(
                                                    this,
                                                    DeafblindChatActivity.HAS_NOT_FRIEND,
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                                )
                                            )
                                            finish()
                                        }, 2500)
                                    }
                                    else -> {
                                        Handler().postDelayed({
                                            startActivity(
                                                DeafblindChatActivity.getIntent(
                                                    this,
                                                    DeafblindChatActivity.HAS_FRIEND,
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                                )
                                            )
                                            finish()
                                        }, 2500)
                                    }
                                }
                            })
                        }
                        false -> {
                            Handler().postDelayed({
                                startActivity(
                                    ChatActivity.getIntent(
                                        this,
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    )
                                )
                                finish()
                            }, 2500)
                        }
                    }
                }
                false -> {
                    Handler().postDelayed({
                        startActivity(
                            MainActivity.getIntent(
                                this
                            )
                        )
                        finish()
                    }, 2500)
                }
            }
//        }, 2500)

    }
}
