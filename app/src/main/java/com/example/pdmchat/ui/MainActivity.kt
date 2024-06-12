package com.example.pdmchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdmchat.R
import com.example.pdmchat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    companion object {
        const val GET_MESSAGES = 1 // Buscar os contatos na contactList do banco de dados
        const val GET_CONTACTS_INTERVAL = 2000L // Intervalo de busca de contatos
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}