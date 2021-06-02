package com.example.capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.capstone.R
import com.example.capstone.model.UserModel
import com.example.capstone.ui.login.LoginActivity
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.database.UserHelper
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.EMAIL_USER
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.FULL_NAME
import com.example.capstone.utils.firestore.FirestoreObject.UserDataTable.Companion.PHONE_NUMBER
import com.example.capstone.utils.firestore.FirestoreServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var btn_signOut:Button
    private lateinit var fullNameText:TextView
    private lateinit var emailText:TextView
    private lateinit var phoneNumberText:TextView
    private lateinit var imageUser:ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationId(view)

        getUserData()

        btn_signOut.setOnClickListener(this)
    }

    private fun getUserData() {
        val authenticationService = AuthenticationService()
        var result = authenticationService.checkUserIsSignIn()
        var email = result?.email

        val firestoreServices = FirestoreServices()
        var getQuery = firestoreServices.UserData().getUserDataByEmail(email.toString())
        getQuery.addOnSuccessListener {
            GlobalScope.launch(Dispatchers.IO) {
                var userModel = UserModel()
                userModel.email = it[EMAIL_USER]?.toString()
                userModel.fullName = it[FULL_NAME]?.toString()
                userModel.phoneNumber = it[PHONE_NUMBER]?.toString()
                setData(userModel)
            }
        }.addOnFailureListener {
            Toast.makeText(this.context, "${it.message.toString()}", Toast.LENGTH_LONG).show()
        }
    }
    private fun setData(user:UserModel){
        fullNameText.text = user.email.toString()
        phoneNumberText.text = user.phoneNumber.toString()
        emailText.text = user.email.toString()
    }

    private fun initializationId(view: View){
        btn_signOut = view.findViewById(R.id.fragmentprofile_signout)
        fullNameText = view.findViewById(R.id.fragmentprofile_fullname)
        emailText = view.findViewById(R.id.fragmentprofile_email)
        imageUser = view.findViewById(R.id.fragmentprofile_imageuser)
        phoneNumberText = view.findViewById(R.id.fragmentprofile_phoneNumber)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fragmentprofile_signout->{
                var authenticationService = AuthenticationService()
                authenticationService.SignOut()
                val intent = Intent(this.context, LoginActivity::class.java)
                startActivity(intent)
                this.activity?.finish()
            }
        }
    }
}