package com.example.projekaplikasibangkitcapstone.ui.lapor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.projekaplikasibangkitcapstone.R

class DetailLaporActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var disasterLocation:TextView

    companion object{
        val IMAGE_REQUEST_TYPE = "IMAGE_REQUEST_TYPE"
        val IMAGE_RESULT = "IMAGE_RESULT"
        val KOTA = "KOTA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapor)
        supportActionBar?.title = "DETAIL SUBMIT REPORT"
        imageView = findViewById(R.id.activity_detail_lapor_imageDisaster)
        disasterLocation = findViewById(R.id.activity_detail_lapor_disasterLocation)

        var extras = intent.extras
        var request = extras?.get(IMAGE_REQUEST_TYPE)
        var image = extras?.get(IMAGE_RESULT)
        var location = extras?.getString(KOTA)
        disasterLocation.text = location.toString()
        if (request=="GALLERY"){
            imageView.setImageURI(image as Uri)
        }else if (request == "CAMERA"){
            imageView.setImageBitmap(image as Bitmap)
        }else{
            //KALAU BUKAN KEDUANYA TULIS NGAPAIN
        }
    }
}