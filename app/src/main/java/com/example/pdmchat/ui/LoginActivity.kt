package com.example.pdmchat.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdmchat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()

            if (username.isNotEmpty()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
                finish()
            } else {
                binding.usernameEditText.error = "Por favor, insira um nome de usuário"
            }
        }
    }
}
