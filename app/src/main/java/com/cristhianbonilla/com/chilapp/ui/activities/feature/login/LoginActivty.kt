package com.cristhianbonilla.com.chilapp.ui.activities.feature.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cristhianbonilla.com.chilapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivty : AppCompatActivity() {

    private val RC_SIGN_IN : Int = 5656
    private val providers = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().build())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val user = FirebaseAuth.getInstance().currentUser

        if(user==null){
            showSingUpOptinons()
        }

    }

    private  fun showSingUpOptinons(){
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val response : IdpResponse? = IdpResponse.fromResultIntent(data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                Toast.makeText(this, user?.email, Toast.LENGTH_LONG).show();
                // ...
            } else {
                Toast.makeText(this, response?.error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
