package com.everyone.cantalk.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.everyone.cantalk.R
import com.everyone.cantalk.factory.ViewModelFactory
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.fragment.dialog.AnnouncementDialogFragment
import com.everyone.cantalk.util.SharedPrefUtil

abstract class BaseFragment <VM: ViewModel, BD : ViewDataBinding>(private val vm: Class<VM>, private val layout: Int) : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: BD
    private val sharedPrefUtil: SharedPrefUtil by lazy {SharedPrefUtil(context!!)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance()).get(vm)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
    }

    protected fun showError(title: String, secondary: String = "") {
        val fm = childFragmentManager
        val alertDialog = AnnouncementDialogFragment.getInstance(R.drawable.error_v1, title, secondary)
        alertDialog.show(fm, "error")
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