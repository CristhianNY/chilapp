package com.cristhianbonilla.com.chilapp.ui.activities.feature.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.feature.login.LoginActivty
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val background = object : Thread(){
            override fun run() {
                super.run()

                try {
                    Thread.sleep(5000)

                    val intent = Intent(baseContext, LoginActivty::class.java)
                    startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}
