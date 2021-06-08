package com.example.capstone.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminaplikasicapstone.utils.adapter.ListDisasterCaseAdapter
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels
import com.example.capstone.model.LangkahPertamaModel
import com.example.capstone.model.PenanggulanganBencanaModel
import com.example.capstone.ui.home.penanggulanganbencana.LangkahPertamaAdapter
import com.example.capstone.ui.home.penanggulanganbencana.PenanggulanganBencanaAdapter
import com.example.capstone.utils.ConvertTime
import com.example.capstone.utils.firebasestorage.FirebasestorageServices
import com.example.capstone.utils.firestore.FirestoreObject
import com.example.capstone.utils.firestore.FirestoreServices
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    //ID LAYOUT
    private lateinit var layoutPenanggulanganBencana:RecyclerView
    private lateinit var aboutAncana:TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity!=null){
            layoutPenanggulanganBencana = view.findViewById(R.id.fragmenthome_recycleviewlayout_penanggulanganbencana)
            aboutAncana = view.findViewById(R.id.fragmenthome_title_aboutAncana)

            val viewModel =ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
            val dataPenanggulanganBencana = viewModel.getDataPenanggulanganBencana()
            var adapter = PenanggulanganBencanaAdapter()
            adapter.getDataPenanggulanganBencana(dataPenanggulanganBencana)

            adapter.setOnItemClickCallback(object : PenanggulanganBencanaAdapter.OnItemClickCallback{
                override fun onItemClicked(data: String) {
                    clicked(data)
//                    Toast.makeText(this@HomeFragment.context, data, Toast.LENGTH_SHORT).show()
                }
            })

            layoutPenanggulanganBencana.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            layoutPenanggulanganBencana.adapter = adapter

            var pemadam:ImageView = view.findViewById(R.id.logo_pemadam_home)
            var basarnas:ImageView = view.findViewById(R.id.logo_basarnas_home)
            var polisi:ImageView = view.findViewById(R.id.logo_polisi_home)
            var bnpb:ImageView = view.findViewById(R.id.logo_bnpb_home)
            var kemenkes:ImageView = view.findViewById(R.id.logo_kemenkes)

            pemadam.setOnClickListener {
                call(view, "113")
            }
            basarnas.setOnClickListener {
                call(view, "115")
            }
            polisi.setOnClickListener {
                call(view, "110")
            }
            bnpb.setOnClickListener {
                call(view, "117")
            }
            kemenkes.setOnClickListener {
                call(view, "119")
            }
        }
    }



    private fun call(view: View, input: String){
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:" + input)
        startActivity(dialIntent)
    }
    private fun clicked(data: String){
        when(data){
            "Gempa Bumi" -> {
                var intent = Intent(this@HomeFragment.context, LangkahPertamaActivity::class.java)
                intent.putExtra(LangkahPertamaActivity.EXTRA_TYPE, "gempa")
                intent.putExtra(LangkahPertamaActivity.EXTRA_TITLE, "Gempa Bumi")
                startActivity(intent)
            }
            "Banjir" -> {
                var intent = Intent(this@HomeFragment.context, LangkahPertamaActivity::class.java)
                intent.putExtra(LangkahPertamaActivity.EXTRA_TYPE, "banjir")
                intent.putExtra(LangkahPertamaActivity.EXTRA_TITLE, "Banjir")
                startActivity(intent)
            }
            "Kebakaran" -> {
                var intent = Intent(this@HomeFragment.context, LangkahPertamaActivity::class.java)
                intent.putExtra(LangkahPertamaActivity.EXTRA_TYPE, "kebakaran")
                intent.putExtra(LangkahPertamaActivity.EXTRA_TITLE, "Kebakaran")
                startActivity(intent)
            }
            "Angin Topan" -> {
                var intent = Intent(this@HomeFragment.context, LangkahPertamaActivity::class.java)
                intent.putExtra(LangkahPertamaActivity.EXTRA_TYPE, "anginPutingBeliung")
                intent.putExtra(LangkahPertamaActivity.EXTRA_TITLE, "Angin Topan")
                startActivity(intent)
            }
            else -> Log.d("homeFragemet", "fail to set EXTRA TYPE")
        }

    }
}