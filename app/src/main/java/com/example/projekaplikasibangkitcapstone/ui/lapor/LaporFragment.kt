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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projekaplikasibangkitcapstone.R


class LaporFragment : Fragment() {

    private lateinit var laporViewModel:LaporViewModel

    //ID LAYOUT
    private lateinit var btn_ambilfoto:Button
    private lateinit var btn_ambildarigaleri:Button
    private lateinit var camera:ImageView

    private val REQUEST_CODE = 42

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
        btn_ambilfoto = view.findViewById(R.id.fragmentlapor_btn_tookphoto)
        camera = view.findViewById(R.id.fragmentlapor_camera)

        btn_ambilfoto.setOnClickListener {
            val ambilfotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(ambilfotoIntent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            var fotoYgDiAmbil = data?.extras?.get("data") as Bitmap
            camera.setImageBitmap(fotoYgDiAmbil)
//            camera.setImageResource(R.drawable.ic_camera)
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}