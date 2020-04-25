package com.everyone.cantalk.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import com.everyone.cantalk.ui.chat.guardian.ChatActivity
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityRegisterBinding
import com.everyone.cantalk.ui.fragment.dialog.AnnouncementDialogFragment
import com.everyone.cantalk.ui.fragment.dialog.ConfirmationDialogFragment
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.chat.deafblind.DeafblindChatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>(RegisterViewModel::class.java, R.layout.activity_register), View.OnClickListener {

    private var disabled : Boolean = false
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun setListener() {
        super.setListener()
        binding.btnYesDisabled.setOnClickListener(this)
        binding.btnNoDisabled.setOnClickListener(this)
        binding.btnSignUp.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_yes_disabled -> {
                if (!disabled) {
                    showConfirmation("Are you disabled?", "", "Your room chat will be showed for disabled person", R.drawable.disabled_v1) {
                        disabled = it
                        setButtonDefault(binding.btnYesDisabled)
                        setButtonBorder(binding.btnNoDisabled)
                    }
                }
            }
            R.id.btn_no_disabled -> {
                disabled = false
                setButtonDefault(binding.btnNoDisabled)
                setButtonBorder(binding.btnYesDisabled)
            }
            R.id.btn_sign_up -> {
                val name = if(binding.etName.text.toString().isEmpty()) "" else binding.etName.text.toString()
                val email = if(binding.etEmail.text.toString().isEmpty()) "" else binding.etEmail.text.toString()
                val password = if(binding.etPassword.text.toString().isEmpty()) "" else binding.etPassword.text.toString()

                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                    register(name, disabled, email, password)
                else {
                    val fm = supportFragmentManager
                    val alertDialog = AnnouncementDialogFragment.getInstance(R.drawable.error_v1, "Oops! Register is failed", "Please fill all the text field")
                    alertDialog.show(fm, "text_field_empty_confirmation")
                }
            }
        }
    }

    private fun register(name: String, isDisabled: Boolean, email: String, password: String) {

        binding.btnSignUp.background = ResourcesCompat.getDrawable(resources,R.drawable.bg_button_disabled,null)
        binding.btnSignUp.isEnabled = false
        binding.btnSignUp.text = resources.getString(R.string.loading)

        viewModel.firebaseSignUpWithEmail(name, isDisabled, email, password, { userId ->
            successListener()
            save(User(userId, name, isDisabled))
        }, {
            successDelete()
        }, {
            failedListener()
        })
    }

    private fun successListener() {
        Log.d("<DEBUG LOGIN>", disabled.toString())

        when(disabled) {
            true -> {
                startActivity(
                    DeafblindChatActivity.getIntent(
                        this@RegisterActivity,
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                )
                finish()
            }
            false -> {
                startActivity(
                    ChatActivity.getIntent(
                        this@RegisterActivity,
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                )
                finish()
            }
        }
    }

    private fun successDelete() {
        val fm = supportFragmentManager
        val alertDialog = AnnouncementDialogFragment.getInstance(
            R.drawable.error_v1, "Oops! Login is failed", "We are sorry, please try again")
        alertDialog.show(fm, "failed_confirmation_deleted")
        binding.btnSignUp.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.bg_button_default,
            null
        )
        binding.btnSignUp.isEnabled = true
        binding.btnSignUp.text = resources.getString(
            R.string.sign_up
        )
    }

    private fun failedListener() {
        val fm = supportFragmentManager
        val alertDialog = AnnouncementDialogFragment.getInstance(R.drawable.error_v1, "Oops! Login is failed", "We are sorry for our mistake, please try again")
        alertDialog.show(fm, "failed_confirmation")
        binding.btnSignUp.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_button_default, null)
        binding.btnSignUp.isEnabled = true
        binding.btnSignUp.text = resources.getString(R.string.sign_up)
    }

    private fun setButtonDefault(button: Button) {
        button.background = getDrawable(R.drawable.bg_button_default)
        button.setTextColor(ResourcesCompat.getColor(resources, android.R.color.white, null))
    }

    private fun setButtonBorder(button: Button) {
        button.background = getDrawable(R.drawable.bg_button_border_gray)
        button.setTextColor(ResourcesCompat.getColor(resources, android.R.color.darker_gray, null))
    }
}
