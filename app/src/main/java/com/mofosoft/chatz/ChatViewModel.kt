package com.mofosoft.chatz

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.mofosoft.chatz.data.USER_NODE
import com.mofosoft.chatz.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth : FirebaseAuth,
    val db : FirebaseFirestore
) : ViewModel() {

    lateinit var context : Context
    var inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    var logIn = mutableStateOf(false)
    var signIn = mutableStateOf(false)

    init {
        val currentUser = auth.currentUser
        logIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }
    fun registerUser(name : String, number: String, email : String, password : String){
        db.collection(USER_NODE).whereEqualTo("number", number).get().addOnSuccessListener {
            inProgress.value = true
            if(it.isEmpty){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            signIn.value = true
                            inProgress.value = false
                            Log.d("check", "${it.isSuccessful}")
                            createOrUpdateProfile(name, number)
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
                        handleException(it,"Failed to Sign in")
                        inProgress.value = false
                    }
            }
            else{
                handleException(msg = "Mobile Number already exists")
                inProgress.value = false
            }
        }
            .addOnFailureListener {
                handleException(it, it.message)
            }

    }

    private fun createOrUpdateProfile(name: String, number: String, imageUrl: String? = null){
        val uId = auth.currentUser?.uid
        val userData = UserData(
            userId = uId,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl
        )

        uId?.let {
            inProgress.value = true
            db.collection(USER_NODE).document(uId).get()
                .addOnSuccessListener {
                    if(it.exists()){
                        //update user data
                    }
                    else {
                        db.collection(USER_NODE).document(uId).set(userData)
                        inProgress.value = false
                        getUserData(uId)
                    }
                }
                .addOnFailureListener{
                    handleException(it, "Cannot Retrieve User")
                    inProgress.value = false
                }
        }
    }

    private fun getUserData(uId: String) {
        inProgress.value = true
        db.collection(USER_NODE).document(uId).addSnapshotListener {
            value, error ->
                if(error != null){
                    handleException(error, "Cannot Retrieve User")
                }
                if(value != null){
                    var user =  value.toObject<UserData>()
                    userData.value = user
                    inProgress.value = false
                }
        }
    }

    private fun handleException(e : Exception?=null, msg : String?){
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun LoginUser(email : String, password : String){
        inProgress.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                inProgress.value = false
                if(it.isSuccessful) {
                    logIn.value = true
                }
            }
            .addOnFailureListener {
                inProgress.value = false
                handleException(it, it.message)
            }
    }
}