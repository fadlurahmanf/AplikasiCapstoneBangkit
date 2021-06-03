package com.example.capstone.ui.lapor

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.example.capstone.HomePageActivity
import com.example.capstone.R
import com.example.capstone.SplashScreen
import com.example.capstone.utils.ConvertImage
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.database.DisasterCaseHelper
import com.example.capstone.utils.firebasestorage.FirebasestorageServices
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_CASE_DETAIL
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_CASE_LOCATION
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_CASE_STATUS
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_DATE_TIME
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_ID_CASE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_IMAGE_CASE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_LATITUDE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_DISASTER_LONGITUDE
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_REPORT_BY_EMAIL
import com.example.capstone.utils.firestore.FirestoreObject.DisasterCaseDataTable.Companion.COL_REPORT_BY_PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreServices
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class DetailLaporActivity : AppCompatActivity(), View.OnClickListener {

    //ID LAYOUT
    private lateinit var imageView: ImageView
    private lateinit var disasterLocation:TextView
    private lateinit var btn_submit:Button
    private lateinit var disasterType:TextView
    private lateinit var disasterCaseDetail:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var linearlayout1:LinearLayout
    private lateinit var linearlayout2:LinearLayout
    private lateinit var linearlayout3:LinearLayout

    lateinit var imageCase:Bitmap
    private lateinit var disasterCaseHelper: DisasterCaseHelper
    lateinit var emailUser:String
    lateinit var phoneNumberUser:String
    lateinit var dateTimeNow:LocalDateTime

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTimeNow = LocalDateTime.now()
        }



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
        getDataQuery.addOnCompleteListener {
            var result = it.result
            phoneNumberUser = result[PHONE_NUMBER].toString()
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
        disasterCaseData.put(COL_DISASTER_DATE_TIME, System.currentTimeMillis().toString())
        disasterCaseData.put(COL_REPORT_BY_EMAIL, emailUser.toString())
        disasterCaseData.put(COL_DISASTER_IMAGE_CASE, "${extras?.getString(ID_CASE)}.png")
        disasterCaseData.put(COL_REPORT_BY_PHONE_NUMBER, phoneNumberUser.toString())
        disasterCaseData.put(COL_DISASTER_CASE_LOCATION, extras?.get(LOCATION).toString())
        disasterCaseData.put(COL_DISASTER_LATITUDE, extras?.get(LATITUDE).toString())
        disasterCaseData.put(COL_DISASTER_LONGITUDE, extras?.get(LONGITUDE).toString())
        disasterCaseData.put(COL_DISASTER_CASE_DETAIL, disasterCaseDetail.text.toString())
        disasterCaseData.put(COL_DISASTER_CASE_STATUS, "waiting")
        var insertQuery = firestoreServices.DisasterCaseData().insertDisasterCaseData(disasterCaseData)
        insertQuery.addOnSuccessListener {
            insertImageDisasterCaseToFirebaseStorage()
        }.addOnFailureListener {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializationIdLayout(){
        imageView = findViewById(R.id.activity_detail_lapor_imageDisaster)
        disasterLocation = findViewById(R.id.activity_detail_lapor_disasterLocation)
        btn_submit = findViewById(R.id.activity_detail_lapor_btnSubmit)
        disasterType = findViewById(R.id.activity_detail_lapor_disasterType)
        progressBar = findViewById(R.id.activity_detail_lapor_loadingBar)
        disasterCaseDetail = findViewById(R.id.activity_detail_lapor_disasterDetail)
        linearlayout1 = findViewById(R.id.activity_detail_lapor_linearLayout1)
        linearlayout2 = findViewById(R.id.activity_detail_lapor_linearLayout2)
        linearlayout3 = findViewById(R.id.activity_detail_lapor_linearLayout3)

        btn_submit.setOnClickListener(this)

        progressBar.visibility = View.INVISIBLE
    }

    private fun insertImageDisasterCaseToFirebaseStorage(){
        var imageBitmap = (imageView.drawable as BitmapDrawable).bitmap
        var baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        var data = baos.toByteArray()
        var firebasestorageServices = FirebasestorageServices()
        var insertQuery = firebasestorageServices.disasterCaseData().insertImageDisasterCase("${intent.extras?.getString(ID_CASE)}.png", data)
        insertQuery.addOnSuccessListener {
            Toast.makeText(this, "BERHASIL UPLOAD", Toast.LENGTH_LONG).show()
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            progressBar.visibility = View.INVISIBLE
            imageView.visibility = View.VISIBLE
            linearlayout1.visibility = View.VISIBLE
            linearlayout2.visibility = View.VISIBLE
            linearlayout3.visibility = View.VISIBLE
            btn_submit.visibility = View.VISIBLE
            Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_detail_lapor_btnSubmit->{
                progressBar.visibility = View.VISIBLE
                imageView.visibility = View.INVISIBLE
                linearlayout1.visibility = View.INVISIBLE
                linearlayout2.visibility = View.INVISIBLE
                linearlayout3.visibility = View.INVISIBLE
                btn_submit.visibility = View.INVISIBLE
                insertDisasterCaseData()
            }
        }
    }

    private fun getDateByTimeStamp(timestamp:Long){
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
        val netDate = Date(timestamp)
        println(sdf.format(netDate))
    }

}