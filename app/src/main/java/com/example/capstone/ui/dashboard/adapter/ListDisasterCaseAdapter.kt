package com.example.adminaplikasicapstone.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels

class ListDisasterCaseAdapter(var listDisasterCase: ArrayList<DisasterCaseDataModels>):RecyclerView.Adapter<ListDisasterCaseAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback:OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:DisasterCaseDataModels)
    }

    fun setOnItemClickCallback(onItemClickCallback:OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var disasterCaseType: TextView = itemView.findViewById(R.id.item_title_dashboard)
        var disasterCasePhoto:ImageView = itemView.findViewById(R.id.item_foto_dashboard)
        var disasterCaseLocation:TextView = itemView.findViewById(R.id.item_content_dashboard)
        var disasterCaseDateTime:TextView = itemView.findViewById(R.id.item_waktu_dashboard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDisasterCase.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var disasterCaseData = listDisasterCase[position]

        Glide.with(holder.disasterCasePhoto).load(disasterCaseData.disasterCaseDataPhoto).into(holder.disasterCasePhoto)
        holder.disasterCaseLocation.text = disasterCaseData.disasterLocation.toString()
        holder.disasterCaseDateTime.text = disasterCaseData.disasterDateTime.toString()
        holder.disasterCaseType.text = disasterCaseData.disasterType.toString()

        holder.itemView.setOnClickListener {
            println(disasterCaseData.disasterCaseDataPhoto)
        }

    }

}