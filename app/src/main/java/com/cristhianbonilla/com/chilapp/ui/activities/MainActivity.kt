package com.cristhianbonilla.com.chilapp.ui.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.base.BaseActivity
import com.cristhianbonilla.com.chilapp.ui.activities.login.LoginActivty
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.home.HomeDomain
import com.cristhianbonilla.com.chilapp.domain.login.LoginDomain
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

private const val PERMISSION_REQUEST = 10

class MainActivity : BaseActivity() {

    @Inject
    lateinit var loginDomain : LoginDomain

    @Inject
    lateinit var homeDomain: HomeDomain

    var permissionIsGranted : Boolean = true

    private var permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getComponent().inject(this)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val model = ViewModelProviders.of(this)[MainViewModel::class.java]


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if(checkPermissions(this,permissions)){
            model.select(true)
            Toast.makeText(this,"Permisos aprobados", Toast.LENGTH_SHORT).show()
        }else{

                requestPermissions(permissions,
                    PERMISSION_REQUEST
                )
            model.select(false)
            }
        }
        setSupportActionBar(toolbar_support)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_notifications
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
    fun checkPermissions(context: Context,permissionsArray:Array<String>):Boolean{

        var allSuccess = true

        for (i in permissionsArray.indices){
            if(checkCallingOrSelfPermission(permissionsArray[i]) == PackageManager.PERMISSION_DENIED){
                allSuccess = false
                permissionIsGranted = false
            }
        }
        return allSuccess
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var allSuccess = true
        if(requestCode == PERMISSION_REQUEST){
            for (i in permissions.indices){
                allSuccess = false

                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])

                    if(requestAgain){
                        Toast.makeText(this,"permiso rechazado", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Ve a Ajustes y dale permisos a la apo", Toast.LENGTH_SHORT).show()
                    }
                    } else {
                }
            }

        }
        if(allSuccess){
            Toast.makeText(this,"Todos los permisos aprobados", Toast.LENGTH_SHORT).show()
        }
    }
}

