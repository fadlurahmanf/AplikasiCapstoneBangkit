package com.example.capstone.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels

class ListNotification(var listDisasterCase: ArrayList<DisasterCaseDataModels>): RecyclerView.Adapter<ListNotification.ListViewHolder>() {
    private lateinit var onItemClickCallback:OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data:DisasterCaseDataModels)
    }

    fun setOnItemClickCallback(onItemClickCallback:OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var disasterCaseType: TextView = itemView.findViewById(R.id.item_title_notification)
        var disasterCasePhoto: ImageView = itemView.findViewById(R.id.item_foto_notification)
        var disasterCaseLocation: TextView = itemView.findViewById(R.id.item_content_notification)
        var disasterCaseDateTime: TextView = itemView.findViewById(R.id.item_waktu_notification)
        var disasterCaseProgress: TextView = itemView.findViewById(R.id.item_progress_notification)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
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
        holder.disasterCaseProgress.text = disasterCaseData.disasterCaseStatus.toString()

    }
}