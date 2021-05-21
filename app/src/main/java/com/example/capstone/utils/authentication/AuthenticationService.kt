package com.example.capstone.utils.authentication

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

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