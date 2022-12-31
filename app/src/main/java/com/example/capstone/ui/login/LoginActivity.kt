package com.example.capstone.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.capstone.HomePageActivity
import com.example.capstone.R
import com.example.capstone.ui.register.RegisterActivity
import com.example.capstone.utils.authentication.AuthenticationService
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var btn_login:Button
    private lateinit var btn_regis:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.loginactivity_usernametext)
        password = findViewById(R.id.loginactivity_passwordtext)
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
                finish()
//                if (isAllEditTextIsNotEmpty()){
//                    var authenticationService = AuthenticationService()
//                    var result = authenticationService.SignIn(email.text.toString(), password.text.toString())
//                    result.addOnCompleteListener {
//                        println("halo")
//                        if (it.isSuccessful){
//                            val intent = Intent(this, HomePageActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }else{
//                            var exception = it.exception as FirebaseAuthException
//                            println(exception.errorCode)
//                        }
//                    }
//                }
            }
            R.id.loginactivity_btn_regis->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun isAllEditTextIsNotEmpty(): Boolean {
        var booleanIsAllEditTextIsNotEmpty = false
        if (email.text.trim().isNotEmpty() && password.text.trim().isNotEmpty()){
            booleanIsAllEditTextIsNotEmpty = true
        }else if (email.text.trim().isNullOrEmpty()){
            email.error = "SILAHKAN ISI EMAIL"
            booleanIsAllEditTextIsNotEmpty = false
        }else if (password.text.trim().isNullOrEmpty()){
            password.error = "SILAHKAN ISI PASSWORD"
            booleanIsAllEditTextIsNotEmpty = false
        }else{
            booleanIsAllEditTextIsNotEmpty = false
        }
        return booleanIsAllEditTextIsNotEmpty
    }
}