package com.example.projekaplikasibangkitcapstone.ui.register

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.projekaplikasibangkitcapstone.R
import com.example.projekaplikasibangkitcapstone.ui.login.LoginActivity
import com.example.projekaplikasibangkitcapstone.utils.database.DataHelper
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_EMAIL
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PASSWORD
import com.example.projekaplikasibangkitcapstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PHONE_NUMBER

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
                var resultGetDataByEmail = dataHelper.getDataByEmail(email.text.toString())
                var values = ContentValues()
                if (resultGetDataByEmail.count>0){
                    values.put(COL_FULL_NAME, full_name.text.toString())
                    values.put(COL_PHONE_NUMBER, phoneNumber.text.toString())
                    values.put(COL_PASSWORD, password.text.toString())
                    dataHelper.update(email.text.toString(), values)
                    println("SEHARUSNYA BERHASIL UPDATE")
                }else{
                    values.put(COL_EMAIL, email.text.toString())
                    values.put(COL_FULL_NAME, full_name.text.toString())
                    values.put(COL_PHONE_NUMBER, phoneNumber.text.toString())
                    values.put(COL_PASSWORD, password.text.toString())
                    dataHelper.insert(values)
                    println("SEHARUSNYA INSERT")
                }
            }
        }
    }
}