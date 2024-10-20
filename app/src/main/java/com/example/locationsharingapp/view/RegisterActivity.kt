package com.example.locationsharingapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.locationsharingapp.R
import com.example.locationsharingapp.databinding.ActivityRegisterBinding
import com.example.locationsharingapp.viewmodel.AthenticationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authenticationViewModel: AthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        authenticationViewModel = ViewModelProvider(this).get(AthenticationViewModel::class.java)

        binding.registerBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passwordEt.text.toString()
            authenticationViewModel.register(email, password,{
            startActivity(Intent(this, MainActivity::class.java))
                finish()

            },{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        }
    }
    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        }
    }
}