package com.example.projekaplikasibangkitcapstone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.projekaplikasibangkitcapstone.HomePageActivity
import com.example.projekaplikasibangkitcapstone.R
import com.example.projekaplikasibangkitcapstone.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_login:Button
    private lateinit var btn_regis:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login = findViewById(R.id.loginactivity_btn_login)
        btn_regis = findViewById(R.id.loginactivity_btn_regis)

        btn_login.setOnClickListener(this)
        btn_regis.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.loginactivity_btn_login->{
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            }
            R.id.loginactivity_btn_regis->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }
}