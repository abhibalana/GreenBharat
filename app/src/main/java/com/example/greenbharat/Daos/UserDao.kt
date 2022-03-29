package com.example.greenbharat.Daos

import com.example.greenbharat.Models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.okhttp.internal.DiskLruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    val db = FirebaseFirestore.getInstance()
    val userCollection = db.collection("users")
    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(user.uid).set(it)
            }
        }

    }

    fun getUserById(uid:String) : Task<DocumentSnapshot>{
        return userCollection.document(uid).get()
    }

}