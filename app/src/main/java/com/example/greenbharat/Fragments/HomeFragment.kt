package com.example.greenbharat.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greenbharat.R
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val parent =  inflater.inflate(R.layout.fragment_home, container, false)
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container2,StatusFragment()).commit()
        changeFragment(parent.upperNavigationBar)
        return parent
    }
    fun changeFragment( nav:ChipNavigationBar){
        var fragment :Fragment? =null
        nav.setOnItemSelectedListener(object:ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(id: Int) {
                when(id){
                    R.id.status -> fragment = StatusFragment()
                    R.id.controls -> fragment = controlsFragment()
                }
                activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container2,fragment!!).commit()
            }

        })


    }


}