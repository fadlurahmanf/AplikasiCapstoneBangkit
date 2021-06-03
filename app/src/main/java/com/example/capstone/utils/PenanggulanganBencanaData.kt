package com.example.capstone.utils

import com.example.capstone.R
import com.example.capstone.model.PenanggulanganBencanaModel

object PenanggulanganBencanaData {
    private val namaBencana = arrayOf(
            "Gempa Bumi",
            "Banjir",
            "Kebakaran",
//            "Tanah Longsor",
            "Angin Topan"
    )
    private val fotoBencana = intArrayOf(
            R.drawable.earhquake,
            R.drawable.flood,
            R.drawable.wildfire,
//            R.drawable.landslide,
            R.drawable.hurricane
    )
    val listData:ArrayList<PenanggulanganBencanaModel>
    get() {
        val list = arrayListOf<PenanggulanganBencanaModel>()
        for (position in namaBencana.indices){
            var bencana = PenanggulanganBencanaModel()
            bencana.name = namaBencana[position]
            bencana.photo = fotoBencana[position]
            list.add(bencana)
        }
        return list
    }
}