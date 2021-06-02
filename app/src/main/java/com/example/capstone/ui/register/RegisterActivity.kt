package com.example.capstone.ui.register

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.capstone.R
import com.example.capstone.model.UserModel
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.firebasestorage.FirebasestorageServices
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.EMAIL_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.FULL_NAME
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.IMAGE_PROFILE_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PASSWORD_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreServices
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btn_signIn:TextView
    private lateinit var btn_register:Button
    private lateinit var full_name:EditText
    private lateinit var email:EditText
    private lateinit var imageUser:ImageView
    private lateinit var phoneNumber:EditText
    private lateinit var password:EditText
    private lateinit var conf_password:EditText

    private var checkImage: Uri? = null

    companion object{
        const val REQUEST_PICK_FROM_GALLERY = 0
    }

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
                if (isAllRequirementIsNotEmpty()){
                    if (isPasswordAndConfPasswordIsMatch()){
                        insertImageToFirebaseStorage()
                    }
                }
            }
            R.id.registeractivity_imageUser->{
                tookImageFromGallery()
            }
        }
    }

    private fun tookImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_FROM_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data!=null){
            if (requestCode== REQUEST_PICK_FROM_GALLERY && resultCode== Activity.RESULT_OK){
                var photoFromGalleryUser = data?.data
                checkImage = photoFromGalleryUser!!
                imageUser.setImageURI(photoFromGalleryUser)
            }
        }
    }

    private fun insertImageToFirebaseStorage(){
        var imagePhotoUser = (imageUser.drawable as BitmapDrawable).bitmap
        var baos = ByteArrayOutputStream()
        imagePhotoUser.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var data = baos.toByteArray()
        var firebasestorageServices = FirebasestorageServices()
        var randomPhotoName:String = "${UUID.randomUUID().toString()}.png"
        val insertQuery = firebasestorageServices.userData().insertImageProfileUser("$randomPhotoName", data)
        insertQuery.addOnSuccessListener {
            registerDataToFirestore(randomPhotoName)
        }.addOnFailureListener {
            Toast.makeText(this, "ERROR ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun registerDataToFirestore(randomPhotoName:String){
        val firestoreServices = FirestoreServices()
        var user:MutableMap<String, Any> = HashMap()
        user.put(FULL_NAME, full_name.text.toString())
        user.put(EMAIL_USER, email.text.toString())
        user.put(PHONE_NUMBER, phoneNumber.text.toString())
        user.put(PASSWORD_USER, password.text.toString())
        user.put(IMAGE_PROFILE_USER, randomPhotoName)

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
            imageUser.setImageResource(R.drawable.ic_person_white)
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
    private fun isAllRequirementIsNotEmpty():Boolean{
        if (full_name.text.trim().isNotEmpty() && email.text.isNotEmpty() && phoneNumber.text.isNotEmpty()
                && password.text.isNotEmpty() && conf_password.text.isNotEmpty() && checkImage.toString()!="null"){
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
        }else if (checkImage.toString() == "null"){
            Toast.makeText(this, "IMAGE TIDAK BOLEH DIKOSONGKAN", Toast.LENGTH_LONG).show()
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
        imageUser = findViewById(R.id.registeractivity_imageUser)

        btn_signIn.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        imageUser.setOnClickListener(this)
    }
}