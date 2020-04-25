package com.everyone.cantalk.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everyone.cantalk.R
import com.everyone.cantalk.factory.ViewModelFactory
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.fragment.dialog.AnnouncementDialogFragment
import com.everyone.cantalk.ui.fragment.dialog.ConfirmationDialogFragment
import com.everyone.cantalk.util.SharedPrefUtil

abstract class BaseActivity <VM: ViewModel, BD : ViewDataBinding>(private val vm: Class<VM>, private val layout: Int) : AppCompatActivity() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: BD
    private val sharedPrefUtil: SharedPrefUtil by lazy {SharedPrefUtil(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance()).get(vm)
        binding = DataBindingUtil.setContentView(this, layout)
        setListener()
    }

    protected fun showError(title: String, secondary: String = "") {
        val fm = supportFragmentManager
        val alertDialog = AnnouncementDialogFragment.getInstance(R.drawable.error_v1, title, secondary)
        alertDialog.show(fm, "error")
    }

    protected fun showConfirmation(title: String, secondary: String, description: String, image: Int, onPositiveButtonClicked: (clicked: Boolean) -> Unit) {
        val fragmentManager = supportFragmentManager
        val alertDialog = ConfirmationDialogFragment.getInstance(R.drawable.disabled_v1, title, secondary, description, object: ConfirmationDialogFragment.SetPositiveButtonListener{
            override fun onClick(clicked: Boolean) {
                onPositiveButtonClicked(clicked)
            }
        })
        alertDialog.show(fragmentManager, "disabled_confirmation")
    }

    open fun setListener() {}

    protected fun isLoggedIn() : Boolean{
        return sharedPrefUtil.isLoggedIn()
    }

    protected fun save(user : User){
        sharedPrefUtil.save(user)
    }

    protected fun load() : User {
        return sharedPrefUtil.load()
    }

    protected fun logout(){
        sharedPrefUtil.logout()
    }

}