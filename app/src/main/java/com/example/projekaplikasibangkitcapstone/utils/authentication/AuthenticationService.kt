package com.example.projekaplikasibangkitcapstone.utils.authentication

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthenticationService {
    var firebaseAuth = FirebaseAuth.getInstance()

    fun SignUp(email:String, password:String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    fun SignIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun checkUserIsSignIn(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
    fun SignOut(){
        return firebaseAuth.signOut()
    }
}