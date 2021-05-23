package com.example.capstone.ui.lapor

import android.R.attr.orientation
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
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
import java.io.IOException
import java.io.InputStream


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

    lateinit var imagePath:String

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
            }
        }
    }

    private fun handleBitmap(context: Context, imageUri: Uri): Bitmap? {
        var imageBitmap: Bitmap? = null
        try {
            var MAX_WIDTH:Int = 1024
            var MAX_HEIGTH:Int = 1024

            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            var imageStream = context.contentResolver.openInputStream(imageUri)
            BitmapFactory.decodeStream(imageStream, null, options)
            imageStream?.close()

            options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGTH)

            options.inJustDecodeBounds = false
            imageStream = context.contentResolver.openInputStream(imageUri)
            var img: Bitmap? = BitmapFactory.decodeStream(imageStream, null, options)
            img = rotateImageIfRequired(context, img, imageUri)
            imageBitmap = img!!
        }catch (e:IOException){
            println("handle BITMAP GAGALLLLLLLLL")
            println(e.message.toString())
        }
        return imageBitmap
    }

    lateinit var exifInterface: ExifInterface
    private fun rotateImageIfRequired(context: Context, img: Bitmap?, imageUri: Uri): Bitmap? {
        var input:InputStream? = context.contentResolver.openInputStream(imageUri)
        var imageBitmap:Bitmap? = null
        try {
            exifInterface = ExifInterface(imageUri.path!!)
            var rotation:Int = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            if (rotation==ExifInterface.ORIENTATION_ROTATE_90){
                imageBitmap = rotateImage(img!!, 90)
            }else if (rotation==ExifInterface.ORIENTATION_ROTATE_180){
                imageBitmap = rotateImage(img!!, 180)
            }else if (rotation==ExifInterface.ORIENTATION_ROTATE_270){
                imageBitmap = rotateImage(img!!, 270)
            }else{
                imageBitmap = img
            }
        }catch (e:IOException){
            println("rotate IMAGE GAGALLLLLLLLLLLLLLL")
        }
        return imageBitmap
    }

    private fun rotateImage(imageBitmap: Bitmap, degree:Int): Bitmap? {
        var matrix:Matrix = Matrix()
        matrix.postRotate(degree.toFloat()) //CHECK LATER
        var rotatedImg = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.width, imageBitmap.height, matrix, true)
        imageBitmap.recycle()
        return rotatedImg

    }

    private fun calculateInSampleSize(options:BitmapFactory.Options, reqWidth:Int, reqHeight:Int): Int {
        var height = options.outHeight
        var width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            var heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            var widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            var totalPixels:Float = (width*height).toFloat()
            var totalReqPixelsCap = reqWidth * reqHeight * 2
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }
        }
        return inSampleSize
    }

    fun getImage(imageUri:Uri){
        try {
            var uriFile = contentResolver.openInputStream(imageUri)
            var exifInterface = ExifInterface(uriFile!!)
            var rotation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)

        }catch (e:IOException){
            println("GAGALLLLLLLLLLL")
            println(e.message.toString())
        }
    }

}