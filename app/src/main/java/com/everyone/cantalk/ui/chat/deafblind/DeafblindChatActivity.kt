package com.everyone.cantalk.ui.chat.deafblind

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityDeafblindChatBinding
import com.everyone.cantalk.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class DeafblindChatActivity : BaseActivity<DeafblindChatViewModel, ActivityDeafblindChatBinding>(DeafblindChatViewModel::class.java, R.layout.activity_deafblind_chat) {

    private lateinit var navigationController: NavController

    companion object {
        const val HAS_FRIEND = 1
        const val HAS_NOT_FRIEND = 0
        private const val EXTRA_FRIEND = "extra_friend"
        fun getIntent(ctx: Context, hasFriend: Int, flags: Int = 0) : Intent {
            val intent = Intent(ctx, DeafblindChatActivity::class.java)
            intent.putExtra(EXTRA_FRIEND, hasFriend)
            if (flags != 0)
                intent.addFlags(flags)
            return intent
        }
    }

    override fun setListener() {
        super.setListener()
        navigationController = findNavController(R.id.nav_disabled_mode)
        val extra = intent.getIntExtra(EXTRA_FRIEND, 0)
        when(extra) {
            HAS_NOT_FRIEND -> navigationController.navigate(R.id.addFriendFragment, null, NavOptions.Builder()
                .setPopUpTo(R.id.readingMessageFragment, true)
                .build())
            HAS_FRIEND -> navigationController.navigate(R.id.readingMessageFragment, null, NavOptions.Builder()
                .setPopUpTo(R.id.addFriendFragment, true)
                .build())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.disabled_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.navigation_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(MainActivity.getIntent(this))
                logout()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
