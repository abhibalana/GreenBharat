package com.example.greenbharat.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.greenbharat.Daos.UserDao
import com.example.greenbharat.LoginActivity
import com.example.greenbharat.Models.User
import com.example.greenbharat.R
import com.example.greenbharat.terms
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.android.synthetic.main.fragment_status.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val parent = inflater.inflate(R.layout.fragment_settings, container, false)

        parent.frame4.setOnClickListener {
            logout()
        }
        parent.frame3.setOnClickListener {
            val intent = Intent(activity,terms::class.java)
            startActivity(intent)
        }
        parent.frame2.setOnClickListener {
            Toast.makeText(activity,"Manual Not Registered",Toast.LENGTH_SHORT).show()
        }
        val user = FirebaseAuth.getInstance().currentUser!!
        parent.personName.text= user.displayName
       Glide.with(parent.rootView).load(user.photoUrl.toString()).circleCrop().into(parent.personImage)

return parent
    }

fun logout(){
    FirebaseAuth.getInstance().signOut()
    activity!!.finish()
}


}