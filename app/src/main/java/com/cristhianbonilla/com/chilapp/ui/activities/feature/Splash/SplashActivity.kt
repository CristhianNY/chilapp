package com.cristhianbonilla.com.chilapp.ui.activities.feature.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.MainActivity
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.feature.login.LoginActivty
import com.cristhianbonilla.com.chilapp.ui.activities.feature.register.RegisterActivity
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var loginDomain : LoginDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        (application as App).getComponent().inject(this)
        val background = object : Thread(){
            override fun run() {
                super.run()

                try {
                    Thread.sleep(5000)
                    validateUserLogin()

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }

    private fun validateUserLogin(){

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            val userNamePreference =  loginDomain.getUserNamePreference("nombre",this)

            if(userNamePreference == null){

                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                finish()
                startActivity(intent)

            }else{
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }else{
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            val intent = Intent(this, LoginActivty::class.java)
            finish()
            startActivity(intent)
        }
    }

    }
