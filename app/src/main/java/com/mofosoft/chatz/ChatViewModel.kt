package com.mofosoft.chatz

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth : FirebaseAuth
) : ViewModel() {
    init {

    }
    lateinit var context : Context
    var inProgress = mutableStateOf(false)
    fun registerUser(name : String, number: String, email : String, password : String){
        inProgress.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    inProgress.value = false
                    Log.d("check", "${it.isSuccessful}")
                }
                else{
                    inProgress.value = false
                    Toast.makeText(
                        context,
                        "Failed to Signed in",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(
                    context,
                    "Failed to Signed in",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}