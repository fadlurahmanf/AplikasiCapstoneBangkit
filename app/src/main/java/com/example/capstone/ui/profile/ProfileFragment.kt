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
import com.example.capstone.R
import com.example.capstone.ui.login.LoginActivity
import com.example.capstone.utils.authentication.AuthenticationService
import com.example.capstone.utils.database.UserHelper
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.COL_FULL_NAME
import com.example.capstone.utils.database.DatabaseContract.UserColumns.Companion.COL_PHONE_NUMBER


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var btn_signOut:Button
    private lateinit var fullNameText:TextView
    private lateinit var emailText:TextView
    private lateinit var phoneNumberText:TextView
    private lateinit var imageUser:ImageView

    private lateinit var userHelper: UserHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationId(view)

        userHelper = UserHelper(this.requireContext().applicationContext)
        userHelper.open()

        getUserData()

        btn_signOut.setOnClickListener(this)
    }

    private fun getUserData() {
        var authenticationService = AuthenticationService()
        var result = authenticationService.checkUserIsSignIn()
        var email = result?.email

        var data = userHelper.getDataByEmail(email.toString())
        data.moveToFirst()
        if (data.moveToFirst()){
            var fullName = data.getString(data.getColumnIndex("$COL_FULL_NAME"))
            var phoneNumber = data.getString(data.getColumnIndex("$COL_PHONE_NUMBER"))
            fullNameText.text = fullName.toString()
            phoneNumberText.text = phoneNumber.toString()
            emailText.text = email.toString()
        }else{
            
        }


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