package com.example.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.example.capstone.ui.login.LoginActivity
import com.example.capstone.ui.register.RegisterActivity
import com.example.capstone.utils.authentication.AuthenticationService

class SplashScreen : AppCompatActivity() {
    private lateinit var coba:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        coba = findViewById(R.id.splashscreenimage)
        Handler().postDelayed({
            if (checkIsUserIsLoggedIn()){
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000)

    }

    private fun checkIsUserIsLoggedIn(): Boolean {
        var authenticationService = AuthenticationService()
        var result = authenticationService.checkUserIsSignIn()
        if (result.toString()=="null"){
            return false
        }else{
            return true
        }
    }
}