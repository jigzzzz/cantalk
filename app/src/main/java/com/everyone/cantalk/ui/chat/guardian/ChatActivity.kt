package com.everyone.cantalk.ui.chat.guardian

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.everyone.cantalk.R
import com.everyone.cantalk.base.BaseActivity
import com.everyone.cantalk.databinding.ActivityChatBinding
import com.everyone.cantalk.model.User
import com.everyone.cantalk.ui.addfriend.AddFriendActivity
import com.everyone.cantalk.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ChatActivity : BaseActivity<ChatViewModel, ActivityChatBinding>(ChatViewModel::class.java, R.layout.activity_chat) {

    private val friends : MutableList<User> = mutableListOf()

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
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_friend,
                R.id.navigation_chat
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun setListener() {
        super.setListener()
        viewModel.getFriends(load().id).observe(this, Observer {
            friends.clear()
            friends.addAll(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                val friendId = data?.getStringExtra(AddFriendActivity.EXTRA_FRIEND_ID) ?: ""
                showConfirmation("Do you want to add your friend?", "", "Your friend will be added to you friend list", R.drawable.disabled_v1) {
                    when(User.checkFriend(friends, friendId)){
                        true -> {
                            viewModel.getCurrentUser(friendId).observe(this, Observer {
                                viewModel.addFriend(load().id, it, {
                                    viewModel.addFriend(friendId, load(), {
                                        showError("Friend added", "Yeayy! You are successfully added your friend")
                                    }, {
                                        showError("Failed add your friend", "Please try again to add your friend")
                                    })
                                }, {
                                    showError("Failed add your friend", "Please try again to add your friend")
                                })
                            })
                        }
                        false -> {
                            showError("You are already be friend", "Let's be friend to the other")
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
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
