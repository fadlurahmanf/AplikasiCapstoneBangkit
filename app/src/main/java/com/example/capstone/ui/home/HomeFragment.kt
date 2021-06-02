package com.example.capstone.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.ui.home.penanggulanganbencana.PenanggulanganBencanaAdapter

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
}