package com.example.capstone.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adminaplikasicapstone.utils.adapter.ListDisasterCaseAdapter
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels
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

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var recycleviewlayout: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private var listDisasterCaseData:ArrayList<DisasterCaseDataModels> = ArrayList<DisasterCaseDataModels>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationIdLayout(view)
        getAllDisasterCaseOnProgressData()

        setRecycleViewLayout()
    }

    private fun setRecycleViewLayout() {
        recycleviewlayout.layoutManager = LinearLayoutManager(this.context)
        var adapter = ListDisasterCaseAdapter(listDisasterCaseData)
        recycleviewlayout.adapter = adapter

        adapter.setOnItemClickCallback(object : ListDisasterCaseAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DisasterCaseDataModels) {
//                val disasterData:DisasterCaseDataModels = data
//                var intent = Intent(this@OnProgressFragment.context, DetailLaporanActivity::class.java)
//                intent.putExtra(DetailLaporanActivity.DISASTER_CASE_DATA, disasterData)
//                startActivity(intent)
            }
        })

    }
    private fun getAllDisasterCaseOnProgressData(){
        var firestoreServices = FirestoreServices()
        var getQuery = firestoreServices.DisasterCaseData().getAllDisasterCaseDataByStatus(status = "onProgress")
        listDisasterCaseData.clear()
        getQuery.addOnCompleteListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (it.isSuccessful){
                    loadingProgressBar.visibility = View.INVISIBLE
                    for (document in it.result!!) {

                        var firebaseStorageServices = FirebasestorageServices()
                        var disasterCaseDataModels = DisasterCaseDataModels()
                        disasterCaseDataModels.disasterType = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_TYPE].toString()
                        disasterCaseDataModels.reportByEmail = document[FirestoreObject.DisasterCaseDataTable.COL_REPORT_BY_EMAIL].toString()
                        disasterCaseDataModels.disasterLocation = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_CASE_LOCATION].toString()
                        disasterCaseDataModels.disasterLatitude = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_LATITUDE].toString()
                        disasterCaseDataModels.disasterLongitude = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_LONGITUDE].toString()
                        disasterCaseDataModels.disasterCaseStatus = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_CASE_STATUS].toString()
                        try {
                            var urlImage = firebaseStorageServices.disasterCaseData().getImageURLByName(document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_IMAGE_CASE].toString()).await()
                            disasterCaseDataModels.disasterCaseDataPhoto = urlImage.toString()
                        }catch (e: StorageException){
                            println("NGELEMPAR ERROR BUKANNYA EXCEPTION, DATA DI FIREBASE STORAGE GA ADA")
                        }
                        disasterCaseDataModels.disasterDateTime = ConvertTime.getTimeByTimeStamp(document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_DATE_TIME].toString().toLong()).toString()
                        disasterCaseDataModels.disasterCaseDetail = document[FirestoreObject.DisasterCaseDataTable.COL_DISASTER_CASE_DETAIL].toString()
                        listDisasterCaseData.add(disasterCaseDataModels)

                    }
                    withContext(Dispatchers.Main){
                        setRecycleViewLayout()
                    }
                }
            }
        }.addOnFailureListener {
            loadingProgressBar.visibility = View.VISIBLE
        }
    }

    private fun initializationIdLayout(view: View) {
        recycleviewlayout = view.findViewById(R.id.fragmentdashboard_recycleviewlayout_laporanbencana)
        loadingProgressBar = view.findViewById(R.id.fragmentOnProgress_loadingBar)
    }

}