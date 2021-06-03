package com.example.capstone.ui.home.penanggulanganbencana

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels
import com.example.capstone.model.LangkahPertamaModel

class LangkahPertamaAdapter(var langkahPertama: ArrayList<LangkahPertamaModel>): RecyclerView.Adapter<LangkahPertamaAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var disasterCaseLocation:TextView = itemView.findViewById(R.id.item_todo_title)
        var disasterCaseDateTime:TextView = itemView.findViewById(R.id.item_todo_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail_penanggunalangan, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return langkahPertama.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var disasterCaseData = langkahPertama[position]

        holder.disasterCaseLocation.text = disasterCaseData.todo.toString()
        holder.disasterCaseDateTime.text = disasterCaseData.detail.toString()

        holder.itemView.setOnClickListener {

        }

    }
}