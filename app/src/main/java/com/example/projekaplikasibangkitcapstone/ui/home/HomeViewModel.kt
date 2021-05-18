package com.example.projekaplikasibangkitcapstone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projekaplikasibangkitcapstone.model.PenanggulanganBencanaModel
import com.example.projekaplikasibangkitcapstone.utils.PenanggulanganBencanaData

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getDataPenanggulanganBencana():List<PenanggulanganBencanaModel> =PenanggulanganBencanaData.listData
}