package com.example.greenbharat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_terms.*

class terms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)
        condition.text = "1.This data is provided by the openweather.com. \n\n2.The data here may vary from google search.\n\n3.Your email is recorded for security purpose.\n\n4.It might not work for some locations Its depend on Api. "
    }
}