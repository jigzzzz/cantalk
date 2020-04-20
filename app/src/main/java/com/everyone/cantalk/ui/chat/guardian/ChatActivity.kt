package com.everyone.cantalk.ui.chat.guardian

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.ui.addfriend.AddFriendActivity

class ChatActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 202
        fun getIntent(ctx: Context, flags: Int = 0) : Intent {
            val intent = Intent(ctx, ChatActivity::class.java)
            if (flags != 0)
                intent.addFlags(flags)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_friend,
                R.id.navigation_chat
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                val friendId = data?.getStringExtra(AddFriendActivity.EXTRA_FRIEND_ID) ?: ""
                Toast.makeText(this, friendId, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
