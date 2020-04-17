package com.everyone.cantalk.repository.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everyone.cantalk.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class AuthRepository {

    companion object {
        @Volatile
        var INSTANCE: AuthRepository? = null

        fun getInstance() : AuthRepository {
            if (INSTANCE == null) {
                synchronized(AuthRepository::class.java) {
                    if (INSTANCE == null)
                        INSTANCE = AuthRepository()
                }
            }
            return INSTANCE!!
        }
    }

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: DatabaseReference by lazy {FirebaseDatabase.getInstance().getReference("Users")}

    fun firebaseSignUpWithEmail(name: String, isDisabled: Boolean, email: String, password: String, successListener: (userId: String) -> Unit, successDelete: () -> Unit, failedListener: () -> Unit) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userId = user?.uid
                    val dbRef = database.child(userId!!)
                    dbRef.setValue(User.hashUser(
                        User(
                            userId,
                            name,
                            isDisabled
                        )
                    )).addOnCompleteListener { p0 ->
                        if (p0.isSuccessful) {
                            successListener(userId)
                        } else {
                            val credential = EmailAuthProvider.getCredential(email, password)
                            user.reauthenticate(credential).addOnCompleteListener {
                                user.delete().addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        successDelete()
                                    }
                                }
                            }
                        }
                    }
                } else {
                    failedListener()
                }
            }
    }

    fun firebaseSignInWithEmail(email: String, password: String, successListener: () -> Unit, failedListener: () -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                successListener()
            }
            else {
                failedListener()
            }
        }
    }

    fun getCurrentUserData(userId: String) : LiveData<User> {
        val dbRef = database.child(userId)
        val userMutableLiveData : MutableLiveData<User> = MutableLiveData()

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w(AuthRepository::class.java.simpleName, "loadDataUser:onCancelled", p0.toException())
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)!!
                user.id = userId
                Log.d("<DEBUG LOGIN>", user.toString())
                userMutableLiveData.postValue(user)
            }
        })

        return userMutableLiveData
    }

}