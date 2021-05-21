package com.example.capstone.ui.lapor

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.capstone.R
import com.example.capstone.utils.ConvertImage
import com.example.capstone.utils.database.DisasterCaseHelper
import com.example.capstone.utils.database.DatabaseContract.DisasterColumns.Companion.COL_DISASTER_IMAGE_CASE
import com.example.capstone.utils.database.DatabaseContract.DisasterColumns.Companion.COL_ID_DISASTER_CASE
import com.example.capstone.utils.database.DatabaseContract.DisasterColumns.Companion.COL_LOCATION
import java.util.*

class DetailLaporActivity : AppCompatActivity(), View.OnClickListener {

    //ID LAYOUT
    private lateinit var imageView: ImageView
    private lateinit var disasterLocation:TextView
    private lateinit var btn_submit:Button
    private lateinit var disasterType:TextView
    private lateinit var disasterCaseDetail:EditText

    lateinit var imageCase:Bitmap
    private lateinit var disasterCaseHelper: DisasterCaseHelper

    companion object{
        val IMAGE_REQUEST_TYPE = "IMAGE_REQUEST_TYPE"
        val IMAGE_RESULT = "IMAGE_RESULT"
        val KOTA = "KOTA"
        val ID_CASE = "_ID"
        val LATITUDE = "LATITUDE"
        val LONGITUDE = "LONGITUDE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapor)
        supportActionBar?.title = "DETAIL SUBMIT REPORT"

        disasterCaseHelper = DisasterCaseHelper(this)
        disasterCaseHelper.open()

        initializationIdLayout()
        setDataFromIntent()


    }

    private fun setDataFromIntent(){
        var extras = intent.extras
        var request = extras?.get(IMAGE_REQUEST_TYPE)
        var image = extras?.get(IMAGE_RESULT)
        var location = extras?.getString(KOTA)
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

    private fun initializationIdLayout(){
        imageView = findViewById(R.id.activity_detail_lapor_imageDisaster)
        disasterLocation = findViewById(R.id.activity_detail_lapor_disasterLocation)
        btn_submit = findViewById(R.id.activity_detail_lapor_btnSubmit)
        disasterType = findViewById(R.id.activity_detail_lapor_disasterType)
        disasterCaseDetail = findViewById(R.id.activity_detail_lapor_disasterDetail)

        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.activity_detail_lapor_btnSubmit->{
                val randomID = UUID.randomUUID().toString()

                var convertImage = ConvertImage
                var imageInByteArray = convertImage.getBytes(imageCase)


                var contentValues = ContentValues()
                contentValues.put(COL_ID_DISASTER_CASE, randomID)
                contentValues.put(COL_LOCATION, disasterLocation.text.toString())
                contentValues.put(COL_DISASTER_IMAGE_CASE, imageInByteArray)


                var result = disasterCaseHelper.insert(contentValues)
                println(result)

//                var insertQuery = disasterCaseHelper.insert(contentValues)
//                println(insertQuery.toString())
            }
        }
    }
}