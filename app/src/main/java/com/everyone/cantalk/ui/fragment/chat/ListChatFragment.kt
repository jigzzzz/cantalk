package com.everyone.cantalk.ui.fragment.chat

import com.everyone.cantalk.ui.main.MainActivity
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseFragment
import com.everyone.cantalk.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth

class ListChatFragment : BaseFragment<ListChatViewModel, FragmentChatBinding>(ListChatViewModel::class.java, R.layout.fragment_chat) {

    override fun setListener() {
        super.setListener()

        binding.btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(MainActivity.getIntent(context!!))
            logout()
            activity?.finish()
        }
    }

}