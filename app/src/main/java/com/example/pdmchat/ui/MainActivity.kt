package com.example.pdmchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdmchat.R
import com.example.pdmchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}