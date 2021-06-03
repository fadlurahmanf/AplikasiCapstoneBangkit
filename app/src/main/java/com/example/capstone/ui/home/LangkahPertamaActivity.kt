package com.example.capstone.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminaplikasicapstone.utils.adapter.ListDisasterCaseAdapter
import com.example.capstone.R
import com.example.capstone.model.DisasterCaseDataModels
import com.example.capstone.model.LangkahPertamaModel
import com.example.capstone.model.UserModel
import com.example.capstone.ui.home.penanggulanganbencana.LangkahPertamaAdapter
import com.example.capstone.ui.home.penanggulanganbencana.PenanggulanganBencanaAdapter
import com.example.capstone.utils.ConvertTime
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.firebasestorage.FirebasestorageServices
import com.example.capstone.utils.firestore.FirestoreObject
import com.example.capstone.utils.firestore.FirestoreServices
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class LangkahPertamaActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_TITLE = "extra_title"
    }
    private var listLangkahPertama:ArrayList<LangkahPertamaModel> = ArrayList<LangkahPertamaModel>()

    private lateinit var recycleviewlayout: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_langkah_pertama)
        supportActionBar?.hide()

        title = findViewById(R.id.langkah_pertama_title)
        recycleviewlayout = findViewById(R.id.langkah_pertama_recycleView)
        loadingProgressBar = findViewById(R.id.fragmentOnProgress_loadingBar)

        val extraTitle = intent.getStringExtra(EXTRA_TITLE)
        title.setText(extraTitle)
        getData()
//        setRecycleViewLayout()
    }

    private fun setRecycleViewLayout() {
        Log.d("langkah", listLangkahPertama.toString())
        recycleviewlayout.layoutManager = LinearLayoutManager(this)
        var adapter = LangkahPertamaAdapter(listLangkahPertama)
        recycleviewlayout.adapter = adapter

    }

    private fun getData() {
        val firestoreServices = FirestoreServices()
        val type = intent.getStringExtra(EXTRA_TYPE)
        var getQuery = type?.let { firestoreServices.LangkahPertamaData().getPertolongan(it) }
        listLangkahPertama.clear()
        if (getQuery != null) {
            getQuery.addOnSuccessListener {
                GlobalScope.launch(Dispatchers.IO) {
                    loadingProgressBar.visibility = View.INVISIBLE
                    var langkahpertama = LangkahPertamaModel()
                    langkahpertama.todo = it[FirestoreObject.LangkahPertamaData.TODO1]?.toString()
                    langkahpertama.detail = it[FirestoreObject.LangkahPertamaData.DETAIL1]?.toString()
                    listLangkahPertama.add(langkahpertama)
                    Log.d("langkah", listLangkahPertama.toString())
                    var langkahpertama2 = LangkahPertamaModel()
                    langkahpertama2.todo = it[FirestoreObject.LangkahPertamaData.TODO2]?.toString()
                    langkahpertama2.detail = it[FirestoreObject.LangkahPertamaData.DETAIL2]?.toString()
                    listLangkahPertama.add(langkahpertama2)
                    Log.d("langkah", listLangkahPertama.toString())
                    var langkahpertama3 = LangkahPertamaModel()
                    langkahpertama3.todo = it[FirestoreObject.LangkahPertamaData.TODO3]?.toString()
                    langkahpertama3.detail = it[FirestoreObject.LangkahPertamaData.DETAIL3]?.toString()
                    listLangkahPertama.add(langkahpertama3)
                    Log.d("langkah", listLangkahPertama.toString())
                    var langkahpertama4 = LangkahPertamaModel()
                    langkahpertama4.todo = it[FirestoreObject.LangkahPertamaData.TODO4]?.toString()
                    langkahpertama4.detail = it[FirestoreObject.LangkahPertamaData.DETAIL4]?.toString()
                    listLangkahPertama.add(langkahpertama4)
                    Log.d("langkah", listLangkahPertama.toString())
                    if(EXTRA_TYPE == "kebakaran"){
                        var langkahpertama5 = LangkahPertamaModel()
                        langkahpertama5.todo = it[FirestoreObject.LangkahPertamaData.TODO5]?.toString()
                        langkahpertama5.detail = it[FirestoreObject.LangkahPertamaData.DETAIL5]?.toString()
                        listLangkahPertama.add(langkahpertama5)
                        Log.d("langkah", listLangkahPertama.toString())
                    }
                    withContext(Dispatchers.Main){
                        setRecycleViewLayout()
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(this, "${it.message.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }

}