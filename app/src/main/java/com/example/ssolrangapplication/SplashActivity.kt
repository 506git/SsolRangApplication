package com.example.ssolrangapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.text.TextUtils
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ssolrangapplication.common.model.UserModel
import com.google.android.gms.auth.api.credentials.IdToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SsolRangApplication)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = Firebase.auth
        findViewById<SignInButton>(R.id.google_sign_btn).setOnClickListener {
            signIn()
        }

//        Intent(this, MainActivity::class.java).apply {
//            startActivity(this)
//            finish()
//        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "GOOGLE SIGN IN FAILED", e)
            }
        }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser

                updateUI(user)
            } else {
                Log.w(TAG, "signInWithCredential:failure", task.exception)
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?){
        if (user != null) {
            val userInf: UserModel = UserModel(auth.currentUser?.displayName.toString(), auth.currentUser?.email.toString(), auth.currentUser?.photoUrl.toString())
            Intent(this, MainActivity::class.java).apply {
                this.putExtra("userInf",userInf)
                startActivity(this)
                finish()
            }
        }
    }
    companion object {
        private const val TAG = "SplashActivity"
        private const val RC_SIGN_IN = 9001
    }
}