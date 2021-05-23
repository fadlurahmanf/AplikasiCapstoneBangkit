package com.example.capstone.ui.lapor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.exifinterface.media.ExifInterface
import com.example.capstone.R
import com.example.capstone.SplashScreen
import com.example.capstone.utils.ConvertImage
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.database.DisasterCaseHelper
import com.example.capstone.utils.firebasestorage.FirebasestorageServices
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_CASE_DETAIL
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_CASE_LOCATION
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_ID_CASE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_LATITUDE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_LONGITUDE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_REPORT_BY_EMAIL
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_REPORT_BY_PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreServices
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class DetailLaporActivity : AppCompatActivity(), View.OnClickListener {

    //ID LAYOUT
    private lateinit var imageView: ImageView
    private lateinit var disasterLocation:TextView
    private lateinit var btn_submit:Button
    private lateinit var disasterType:TextView
    private lateinit var disasterCaseDetail:EditText

    lateinit var imageCase:Bitmap
    private lateinit var disasterCaseHelper: DisasterCaseHelper
    lateinit var emailUser:String
    lateinit var phoneNumberUser:String

    companion object{
        val IMAGE_REQUEST_TYPE = "IMAGE_REQUEST_TYPE"
        val IMAGE_RESULT = "IMAGE_RESULT"
        val LOCATION = "LOCATION"
        val ID_CASE = "_ID"
        val LATITUDE = "LATITUDE"
        val LONGITUDE = "LONGITUDE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapor)
        supportActionBar?.title = "DETAIL SUBMIT REPORT"

        isUserIsSignIn()
        getUserData()

        disasterCaseHelper = DisasterCaseHelper(this)
        disasterCaseHelper.open()

        initializationIdLayout()
        setDataFromIntent()


    }

    private fun isUserIsSignIn(){
        val authenticationService = AuthenticationService()
        var checkUser = authenticationService.checkUserIsSignIn()
        if (checkUser?.email.toString()!="null"){
            emailUser = checkUser?.email.toString()
        }else{
            val intent = Intent(this, SplashScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getUserData(){
        val firestoreServices = FirestoreServices()
        var getDataQuery = firestoreServices.UserData().getUserDataByEmail(emailUser)
        getDataQuery.addOnSuccessListener {
            phoneNumberUser = it[PHONE_NUMBER].toString()
        }
    }

    private fun setDataFromIntent(){
        var extras = intent.extras
        var request = extras?.get(IMAGE_REQUEST_TYPE)
        var image = extras?.get(IMAGE_RESULT)
        var location = extras?.getString(LOCATION)
        disasterLocation.text = location.toString()
        if (request=="GALLERY"){
            imageView.setImageURI(image as Uri)
            var imageInBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image as Uri?)
            imageCase = imageInBitmap
        }else if (request == "CAMERA"){
            imageView.setImageBitmap(image as Bitmap)
            imageCase = image

        }
    }

    private fun insertDisasterCaseData(){
        var extras = intent.extras
        val firestoreServices = FirestoreServices()
        var disasterCaseData:MutableMap<String, Any> = HashMap()
        disasterCaseData.put(COL_DISASTER_ID_CASE, extras?.get(ID_CASE).toString())
        disasterCaseData.put(COL_REPORT_BY_EMAIL, emailUser.toString())
        disasterCaseData.put(COL_REPORT_BY_PHONE_NUMBER, phoneNumberUser.toString())
        disasterCaseData.put(COL_DISASTER_CASE_LOCATION, extras?.get(LOCATION).toString())
        disasterCaseData.put(COL_DISASTER_LATITUDE, extras?.get(LATITUDE).toString())
        disasterCaseData.put(COL_DISASTER_LONGITUDE, extras?.get(LONGITUDE).toString())
        disasterCaseData.put(COL_DISASTER_CASE_DETAIL, disasterCaseDetail.text.toString())
        var insertQuery = firestoreServices.DisasterCaseData().insertDisasterCaseData(disasterCaseData)
        insertQuery.addOnSuccessListener {
            println("SUKSESSSSSSSSSSSSSSSSS")
        }.addOnFailureListener {
            println("GAGALLLLLLLLLLLLLLLLLLLLLL")
        }
    }

    private fun initializationIdLayout(){
        imageView = findViewById(R.id.activity_detail_lapor_imageDisaster)
        disasterLocation = findViewById(R.id.activity_detail_lapor_disasterLocation)
        btn_submit = findViewById(R.id.activity_detail_lapor_btnSubmit)
        disasterType = findViewById(R.id.activity_detail_lapor_disasterType)
        disasterCaseDetail = findViewById(R.id.activity_detail_lapor_disasterDetail)

        btn_submit.setOnClickListener(this)
    }

    private fun insertImageDisasterCaseToFirebaseStorage(){
        var imageCase = ConvertImage.getUriFromBitmap(imageCase, this)
        var firebasestorageServices = FirebasestorageServices()
        var insertQuery = firebasestorageServices.disasterCaseData().insertImageDisasterCase("${intent.extras?.getString(ID_CASE)}.png", imageCase)
        insertQuery.addOnSuccessListener {
            println("SUKSESSSSSSSSSSSSSSSSSSSSSSSS")
        }.addOnFailureListener {
            println("GAGALLLLLLLLLLLLLLLLLL")
            println(it.message.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_detail_lapor_btnSubmit->{
//                insertDisasterCaseData()
//                insertImageDisasterCaseToFirebaseStorage()

                var imageUri = ConvertImage.getUriFromBitmap(imageCase, this)
                imageView.setImageURI(imageUri)
            }
        }
    }

    fun getImage(imageInBitmap:Bitmap, imageAbsolutePath:String){
        try {
            var exifInterface = ExifInterface(imageAbsolutePath)
            println(exifInterface.toString())
            println("berhasil")
        }catch (e:IOException){
            println("GAGALLL")
            println(e.message)
        }
    }
}