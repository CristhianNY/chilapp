package com.cristhianbonilla.com.chilapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cristhianbonilla.com.chilapp.ui.activities.feature.base.BaseActivity
import com.cristhianbonilla.com.chilapp.ui.activities.feature.login.LoginActivty
import com.cristhianbonilla.com.chilapp.ui.activities.feature.register.RegisterActivity
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var loginDomain : LoginDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getComponent().inject(this)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setSupportActionBar(toolbar_support)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.logAut -> {
               logOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut(){

        loginDomain.deleteeUserPreference(this)
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, LoginActivty::class.java)

                startActivity(intent)
            }
    }



}
