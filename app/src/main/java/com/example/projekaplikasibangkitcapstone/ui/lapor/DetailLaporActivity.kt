package com.example.projekaplikasibangkitcapstone.ui.lapor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.projekaplikasibangkitcapstone.R

class DetailLaporActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var imageView: ImageView
    private lateinit var disasterLocation:TextView
    private lateinit var btn_submit:Button
    private lateinit var disasterType:TextView
    private lateinit var disasterCaseDetail:EditText

    companion object{
        val IMAGE_REQUEST_TYPE = "IMAGE_REQUEST_TYPE"
        val IMAGE_RESULT = "IMAGE_RESULT"
        val KOTA = "KOTA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapor)
        supportActionBar?.title = "DETAIL SUBMIT REPORT"

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
        }else if (request == "CAMERA"){
            imageView.setImageBitmap(image as Bitmap)
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
                println(disasterLocation.text.toString())
                println(disasterType.text.toString())
            }
        }
    }
}