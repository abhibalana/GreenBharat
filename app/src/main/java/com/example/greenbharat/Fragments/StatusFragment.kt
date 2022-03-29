package com.example.greenbharat.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

import android.location.LocationManager

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.greenbharat.MySingleton
import com.example.greenbharat.R
import com.google.android.gms.location.*

import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.android.synthetic.main.fragment_status.view.*
import java.lang.Exception


class StatusFragment : Fragment() {

    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
     var locationRequest: LocationRequest?= null
     var lat:String=" "
     var long:String =""
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {


            val parent =  inflater.inflate(R.layout.fragment_status, container, false)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
            getLastLocation()





        return parent
        }
    @SuppressLint("MissingPermission")
    fun getLastLocation(){
        if(checkPermission()){
            if(isLocationEnabled()){
                mFusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    var location = it.result
                    if(location==null){
                       getNetworkLocation()
                    }
                    else{
                        lat = location.latitude.toString()
                        long = location.longitude.toString()
                        getData()
                        getAirData()





                    }
                }
            }
            else{

                Toast.makeText(activity,"Turn on Location",Toast.LENGTH_SHORT).show()

            }
        }
        else{
            requestPermission()
        }

    }


    @SuppressLint("MissingPermission")
    fun getNetworkLocation(){
        locationRequest = LocationRequest.create().apply {
           priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval=0
            numUpdates=2
        }
        mFusedLocationProviderClient.requestLocationUpdates(
            locationRequest!!,locationCallback,
            Looper.myLooper()!!)


       }
    private val locationCallback = object :LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation = p0.lastLocation
            lat = lastLocation.latitude.toString()
            long = lastLocation.longitude.toString()
            getData()
            getAirData()







        }

    }





    fun isLocationEnabled():Boolean{
        var locationManager  = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }
    fun checkPermission() : Boolean{
        return ContextCompat.checkSelfPermission(activity!!,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity!!,Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
    }
    fun requestPermission(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),100)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             Toast.makeText(activity,"Permission Granted",Toast.LENGTH_SHORT).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    fun getData() {

            val url =
                "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$long&appid="
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {
                locationName.text = it.getString("name")
                location.setImageResource(R.drawable.ic_baseline_location_on_24)
                val json = it.getJSONObject("main")
                data2.text = json.getString("temp")+" degC"
                data5.text = json.getString("pressure")+"hpa"
                data7.text = json.getString("humidity")+"%"
                val json2 = it.getJSONArray("weather").getJSONObject(0)
                data9.text = json2.getString("description").toString()
                val iconid = json2.getString("icon")
                val icon = "https://openweathermap.org/img/wn/$iconid@2x.png"
                Glide.with(activity!!).load(icon).into(data3)





            }, {
                location.setImageResource(R.drawable.ic_baseline_location_off_24)
                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            })
            MySingleton.getInstance(this.requireContext()).addToRequestQueue(jsonObjectRequest)




    }
    fun getAirData(){
        val url = "https://api.openweathermap.org/data/2.5/air_pollution?lat=$lat&lon=$long&appid=ed62a7488ad29061bb20bd05346355fc"
        val jsonObjectRequest1 = JsonObjectRequest(Request.Method.GET,url,null, {

                val array = it.getJSONArray("list").getJSONObject(0)
                 val aqi = array.getJSONObject("main").getString("aqi")
            data11.text=aqi
                 data14.text = array.getJSONObject("components").getString("co")
            data16.text = array.getJSONObject("components").getString("no")
            data18.text = array.getJSONObject("components").getString("so2")
            data20.text = array.getJSONObject("components").getString("pm2_5")

            when(aqi){
                "1" -> data12.text="Good \n Environment"
                "2" -> data12.text="Fair \n Environment"
                "3" -> data12.text="Moderate \n Environment"
                "4" -> data12.text="poor \n Environment"
                "5" -> data12.text="Very Poor \n Environment"

            }



        }, {
            Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        })

        MySingleton.getInstance(this.requireContext()).addToRequestQueue(jsonObjectRequest1)
    }



    }

