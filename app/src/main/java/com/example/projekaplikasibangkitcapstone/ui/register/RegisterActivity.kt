package com.example.projekaplikasibangkitcapstone.ui.register

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projekaplikasibangkitcapstone.R
import com.example.projekaplikasibangkitcapstone.ui.login.LoginActivity
import com.example.projekaplikasibangkitcapstone.utils.authentication.AuthenticationService
import com.example.projekaplikasibangkitcapstone.utils.database.DataHelper
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_EMAIL
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PASSWORD
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PHONE_NUMBER
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_signIn:TextView
    private lateinit var btn_register:Button
    private lateinit var full_name:EditText
    private lateinit var email:EditText
    private lateinit var phoneNumber:EditText
    private lateinit var password:EditText
    private lateinit var conf_password:EditText
    private lateinit var dataHelper: DataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_signIn = findViewById(R.id.registeractivity_btn_signIn)
        btn_register = findViewById(R.id.registeractivity_btn_register)
        full_name = findViewById(R.id.registeractivity_fullname)
        email = findViewById(R.id.registeractivity_email)
        phoneNumber = findViewById(R.id.registeractivity_phonenumber)
        password = findViewById(R.id.registeractivity_password)
        conf_password = findViewById(R.id.registeractivity_confirmpassword)

        btn_signIn.setOnClickListener(this)
        btn_register.setOnClickListener(this)

        dataHelper = DataHelper.getInstance(applicationContext)
        dataHelper.open()

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.registeractivity_btn_signIn->{
                onBackPressed()
            }
            R.id.registeractivity_btn_register->{
                if (isAllTextFieldIsNotEmpty()){
                    if (isPasswordAndConfPasswordIsMatch()){
                        var resultGetDataByEmail = dataHelper.getDataByEmail(email.text.toString())
                        var values = ContentValues()
                        var authenticationService = AuthenticationService()
                        var result = authenticationService.SignUp(email.text.toString(), password.text.toString())
                        result.addOnSuccessListener {
                            println("berhasil")
                            if (resultGetDataByEmail.count>0){
                                values.put(COL_FULL_NAME, full_name.text.toString())
                                values.put(COL_PHONE_NUMBER, phoneNumber.text.toString())
                                values.put(COL_PASSWORD, password.text.toString())
                                dataHelper.update(email.text.toString(), values)
                                println("BERHASIL UPDATE")
                            }else{
                                values.put(COL_EMAIL, email.text.toString())
                                values.put(COL_FULL_NAME, full_name.text.toString())
                                values.put(COL_PHONE_NUMBER, phoneNumber.text.toString())
                                values.put(COL_PASSWORD, password.text.toString())
                                dataHelper.insert(values)
                                println("BERHASL INSERT")
                            }
                            clearAllEditText()
                            Toast.makeText(this, "BERHASIL REGISTRASI", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {
                            println("gagal")
                            var exception = it as FirebaseAuthException
                            println(exception.errorCode)
                            Toast.makeText(this, "${exception.errorCode}", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        println("PASSWORD DAN CONFIRM PASSWORD GA SESUAI")
                    }
                }else{
                    println("ADA YANG BELOM KE ISI")
                }
            }
        }
    }

    private fun clearAllEditText() {
        full_name.setText("")
        email.setText("")
        phoneNumber.setText("")
        password.setText("")
        conf_password.setText("")
    }

    private fun isAllTextFieldIsNotEmpty():Boolean{
        if (full_name.text.trim().isNotEmpty() && email.text.isNotEmpty() && phoneNumber.text.isNotEmpty()
                && password.text.isNotEmpty() && conf_password.text.isNotEmpty()){
            return true
        }else if (full_name.text.trim().isEmpty()){
            full_name.error ="TIDAK BOLEH DIKOSONGKAN"
            return false
        }else if (email.text.trim().isEmpty()){
            email.error ="TIDAK BOLEH DIKOSONGKAN"
            return false
        }else if (phoneNumber.text.trim().isEmpty()){
            phoneNumber.error ="TIDAK BOLEH DIKOSONGKAN"
            return false
        }else if (password.text.trim().isEmpty()){
            password.error ="TIDAK BOLEH DIKOSONGKAN"
            return false
        }else if (conf_password.text.trim().isEmpty()){
            conf_password.error ="TIDAK BOLEH DIKOSONGKAN"
            return false
        }else{
            return false
        }
    }
    private fun isPasswordAndConfPasswordIsMatch():Boolean{
        if (password.text.toString() == conf_password.text.toString()){
            return true
        }else{
            password.error = "PASSWORD DAN CONFIRM PASSWORD HARUS SESUAI"
            conf_password.error = "PASSWORD DAN CONFIRM PASSWORD HARUS SESUAI"
            return false
        }
    }
}