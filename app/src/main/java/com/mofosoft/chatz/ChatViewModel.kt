package com.mofosoft.chatz

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import com.mofosoft.chatz.data.CHATS
import com.mofosoft.chatz.data.ChatData
import com.mofosoft.chatz.data.ChatUser
import com.mofosoft.chatz.data.USER_NODE
import com.mofosoft.chatz.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class ChatViewModel @Inject constructor(
    val auth : FirebaseAuth,
    val db : FirebaseFirestore,
    val storage : FirebaseStorage
) : ViewModel() {

    lateinit var context : Context
    var inProgress = mutableStateOf(false)
    var inProcessChats = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    var logIn = mutableStateOf(false)
    var signIn = mutableStateOf(false)

    val chats = mutableStateOf<List<ChatData>>(listOf())
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

    fun createOrUpdateProfile(name: String? = null, number: String? = null, imageUrl: String? = null){
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
                        db.collection(USER_NODE).document(uId).set(userData, SetOptions.merge())
                            .addOnSuccessListener{
                                inProgress.value = false
                                getUserData(uId)
                            }
                            .addOnFailureListener {
                                handleException(it, "Cannot Update User Data")
                                inProgress.value = false
                            }
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

                    populateChat()
                }
        }
    }

    private fun handleException(e : Exception?=null, msg : String?){
        val errMsg : String? = e?.message ?: msg
        Toast.makeText(
            context,
            errMsg,
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

    }

    fun uploadProfileImage(uri : Uri){
        uploadImage(uri = uri){
            createOrUpdateProfile(
                imageUrl = it.toString()
            )
        }
    }

    fun uploadImage(uri : Uri, onSuccess:(Uri) -> Unit){
        inProgress.value = true
        val storageref = storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageref.child("images/$uuid")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl

            result?.addOnSuccessListener(onSuccess)
            inProgress.value = false
        }
            .addOnFailureListener {
                handleException(it, it.message)
            }
    }

    fun logout(){
        auth.signOut()
        logIn.value = false
        userData.value = null

    }

    fun onAddChat(number : String){
        if(number.isEmpty() || !number.isDigitsOnly()){
            handleException(null, "Enter Valid Number")
        }
        else{
            db.collection(CHATS).where(Filter.or(
                Filter.and(
                    Filter.equalTo("user1.number", number),
                    Filter.equalTo("user2.number", userData.value?.number),
                ),
                Filter.and(
                    Filter.equalTo("user1.number", userData.value?.number),
                    Filter.equalTo("user2.number", number),
                )
            )).get().addOnSuccessListener {
                if(it.isEmpty){
                    db.collection(USER_NODE).whereEqualTo("number", number).get().addOnSuccessListener {
                        if(it.isEmpty){
                            handleException(msg = "Number not found")
                        }
                        else{
                            val chatPartner = it.toObjects<UserData>()[0]
                            val id = db.collection(CHATS).document().id
                            val chat = ChatData(
                                chatId = id,
                                ChatUser(
                                    userId = userData.value?.userId,
                                    name = userData.value?.name,
                                    number = userData.value?.number,
                                    imageUrl = userData.value?.imageUrl
                                ),
                                ChatUser(
                                    userId = chatPartner.userId,
                                    name = chatPartner.name,
                                    number = chatPartner.number,
                                    imageUrl = chatPartner.number
                                )
                            )
                            db.collection(CHATS).document(id).set(chat)
                        }
                    }
                        .addOnFailureListener {
                            handleException(it, it.message)
                        }
                }
                else{
                    handleException(msg = "User already exists")
                }
            }
        }
    }

    fun populateChat(){
        inProcessChats.value = true
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId),
            )
        )
            .addSnapshotListener { value, error ->
                if(error!=null){
                    handleException(msg = error.message)
                }
                if (value != null){
                    chats.value = value.documents.mapNotNull {
                        it.toObject<ChatData>()
                    }
                    inProcessChats.value = false
                }
                inProcessChats.value = false
            }
    }
}