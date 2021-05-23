package com.example.capstone.ui.register

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R
import com.example.capstone.model.UserModel
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.EMAIL_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.FULL_NAME
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PASSWORD_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreServices

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_signIn:TextView
    private lateinit var btn_register:Button
    private lateinit var full_name:EditText
    private lateinit var email:EditText
    private lateinit var phoneNumber:EditText
    private lateinit var password:EditText
    private lateinit var conf_password:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initializationIdLayout()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.registeractivity_btn_signIn->{
                onBackPressed()
            }
            R.id.registeractivity_btn_register->{

                if (isAllTextFieldIsNotEmpty()){
                    if (isPasswordAndConfPasswordIsMatch()){
                        registerDataToFirestore()
                    }
                }
            }
        }
    }

    private fun registerDataToFirestore(){
        val firestoreServices = FirestoreServices()
        var user:MutableMap<String, Any> = HashMap()
        user.put(FULL_NAME, full_name.text.toString())
        user.put(EMAIL_USER, email.text.toString())
        user.put(PHONE_NUMBER, phoneNumber.text.toString())
        user.put(PASSWORD_USER, password.text.toString())

        var insertQuery = firestoreServices.UserData().insertUserData(user)
        insertQuery.addOnSuccessListener {
            registerDataToFirebaseAuthentication()
        }.addOnFailureListener {
            Toast.makeText(this, "${it.message.toString()}", Toast.LENGTH_LONG).show()
        }
    }
    private fun registerDataToFirebaseAuthentication(){
        val authenticationService = AuthenticationService()
        var insertQuery = authenticationService.SignUp(email.text.toString(), password.text.toString())
        insertQuery.addOnSuccessListener {
            Toast.makeText(this, "BERHASIL REGISTRASI", Toast.LENGTH_LONG).show()
            clearAllEditText()
        }.addOnFailureListener {
            Toast.makeText(this, "${it.message.toString()}", Toast.LENGTH_LONG).show()
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

    private fun initializationIdLayout(){
        btn_signIn = findViewById(R.id.registeractivity_btn_signIn)
        btn_register = findViewById(R.id.registeractivity_btn_register)
        full_name = findViewById(R.id.registeractivity_fullname)
        email = findViewById(R.id.registeractivity_email)
        phoneNumber = findViewById(R.id.registeractivity_phonenumber)
        password = findViewById(R.id.registeractivity_password)
        conf_password = findViewById(R.id.registeractivity_confirmpassword)

        btn_signIn.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }
}