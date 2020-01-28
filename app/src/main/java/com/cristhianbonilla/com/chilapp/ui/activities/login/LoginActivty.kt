package com.cristhianbonilla.com.chilapp.ui.activities.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.base.BaseActivity
import com.cristhianbonilla.com.chilapp.ui.activities.register.RegisterActivity
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginActivty : BaseActivity() {

    private val RC_SIGN_IN : Int = 5656
    private val providers = arrayListOf(
        AuthUI.IdpConfig.PhoneBuilder().build())

    @Inject
    lateinit var loginDomain : LoginDomain

    lateinit var bntLogin: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).getComponent().inject(this)

        setContentView(R.layout.login_activity)

        initViews()

        validateUserLogin()

        bntLogin.setOnClickListener(View.OnClickListener {
            showSingUpOptinons()

        })

    }

    private fun initViews(){
        val user = FirebaseAuth.getInstance().currentUser

        bntLogin = findViewById<Button>(R.id.bnt_login)
    }

    private fun validateUserLogin(){

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
        val userNamePreference =  loginDomain.getUserNamePreference("nombre",this)

        if(userNamePreference == null){

            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)

        }else{
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val intent = Intent(this,
                MainActivity::class.java)
            finish()
            startActivity(intent)
         }
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

                validateUserLogin()

                Toast.makeText(this, user?.email, Toast.LENGTH_LONG).show();
                // ...
            } else {
                Toast.makeText(this, response?.error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
