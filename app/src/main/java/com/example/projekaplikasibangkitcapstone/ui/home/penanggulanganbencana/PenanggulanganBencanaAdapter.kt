package com.example.projekaplikasibangkitcapstone.ui.home.penanggulanganbencana

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekaplikasibangkitcapstone.R
import com.example.projekaplikasibangkitcapstone.model.PenanggulanganBencanaModel
import com.google.android.material.imageview.ShapeableImageView

class PenanggulanganBencanaAdapter:RecyclerView.Adapter<PenanggulanganBencanaAdapter.ListViewHolder>() {
    private var listPenanggulanganBencana = ArrayList<PenanggulanganBencanaModel>()

    fun getDataPenanggulanganBencana(data:List<PenanggulanganBencanaModel>){
        if (data!=null){
            this.listPenanggulanganBencana.clear()
            this.listPenanggulanganBencana.addAll(data)
        }
    }

    inner class ListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var namaBencana:TextView = itemView.findViewById(R.id.item_text_namabencana)
        var fotoBencana:ShapeableImageView = itemView.findViewById(R.id.item_foto_penanggulanganbencana)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_penanggulangan_bencana, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPenanggulanganBencana.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val bencana = listPenanggulanganBencana[position]

        holder.namaBencana.text = bencana.name
//        Glide.with(holder.fotoBencana).load(bencana.photo).into(holder.fotoBencana)
//        holder.fotoBencana.setImageResource(bencana.photo!!)
        holder.fotoBencana.setBackgroundResource(bencana.photo!!)
//        holder.constrainlayout.setBackgroundResource(bencana.photo!!)
    }
}