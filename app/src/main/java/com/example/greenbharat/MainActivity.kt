package com.example.greenbharat

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.greenbharat.Fragments.HomeFragment
import com.example.greenbharat.Fragments.PotsFragment
import com.example.greenbharat.Fragments.SettingsFragment
import com.example.greenbharat.Fragments.StatusFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()
        bottomMenu()
    }



    private fun bottomMenu() {
        menu_navigationBar.setOnItemSelectedListener(object: ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(id: Int) {
                var fragment : Fragment? = null
                when(id){
                    R.id.home -> fragment = HomeFragment()
                    R.id.Pots -> fragment = PotsFragment()
                    R.id.settings -> fragment = SettingsFragment()

                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment!!).commit()
            }

        })
    }


}