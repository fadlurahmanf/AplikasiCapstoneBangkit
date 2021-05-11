package com.example.projekaplikasibangkitcapstone.ui.lapor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projekaplikasibangkitcapstone.R


class LaporFragment : Fragment(), View.OnClickListener {

    private lateinit var laporViewModel:LaporViewModel

    //ID VIEW IN LAYOUT
    private lateinit var btn_fromCamera:Button
    private lateinit var btn_fromGallery:Button
    private lateinit var photoResult:ImageView
    private lateinit var btn_clearPhoto:TextView

    private val REQUEST_PICK_FROM_CAMERA = 42
    private val REQUEST_PICK_FROM_GALLERY = 43

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        laporViewModel = ViewModelProvider(this).get(LaporViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_lapor, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_fromCamera = view.findViewById(R.id.fragmentlapor_btn_fromcamera)
        photoResult = view.findViewById(R.id.fragmentlapor_photoresult)
        btn_clearPhoto = view.findViewById(R.id.fragmentlapor_clearphoto)
        btn_fromGallery = view.findViewById(R.id.fragmentlapor_btn_fromgallery)

        btn_fromCamera.setOnClickListener(this)
        btn_clearPhoto.setOnClickListener(this)
        btn_fromGallery.setOnClickListener(this)
        btn_clearPhoto.visibility = View.INVISIBLE

        getLocationAccess()
    }

    private fun getLocationAccess() {
//        if (ContextCompat)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_FROM_CAMERA && resultCode == Activity.RESULT_OK){
            var fotoYgDiAmbil = data?.extras?.get("data") as Bitmap
            photoResult.setImageBitmap(fotoYgDiAmbil)
            btn_clearPhoto.visibility = View.VISIBLE
        }else if (requestCode == REQUEST_PICK_FROM_GALLERY && resultCode == Activity.RESULT_OK){
            var fotoYgDiAmbil = data?.data
            photoResult.setImageURI(fotoYgDiAmbil)
            btn_clearPhoto.visibility = View.VISIBLE
        } else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fragmentlapor_btn_fromcamera->{
                val ambilfotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(ambilfotoIntent, REQUEST_PICK_FROM_CAMERA)
            }
            R.id.fragmentlapor_clearphoto->{
                photoResult.setImageResource(R.drawable.ic_camera)
                btn_clearPhoto.visibility = View.INVISIBLE
            }
            R.id.fragmentlapor_btn_fromgallery->{
                ambilFotoDariGaleri()
            }
        }
    }

    private fun ambilFotoDariGaleri() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_FROM_GALLERY)
    }

}