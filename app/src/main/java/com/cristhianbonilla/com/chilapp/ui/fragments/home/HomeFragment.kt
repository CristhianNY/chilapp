package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.MainViewModel
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.HomeDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var homeDomain: HomeDomain

    @Inject
    lateinit var loginDomain: LoginDomain

    private lateinit var homeViewModel: HomeViewModel
    private var isPermissionsProvide : Boolean = false

    private var permissions = arrayOf(
        Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

    lateinit var ACTIVITY: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        ACTIVITY = context as MainActivity


        if(this.activity?.let { ACTIVITY.checkPermissions(it,permissions) }!!){

            Toast.makeText(context,"Hola si tiene ",Toast.LENGTH_SHORT).show()
            saveContactsToFirebase()
        }else{
            Toast.makeText(context,"No tiene  ",Toast.LENGTH_SHORT).show()
        }

        return root
    }

    fun loadContacts(c: Context) {
        Toast.makeText(context,"QUe pasa  ",Toast.LENGTH_SHORT).show()
    }

    fun saveContactsToFirebase(){

       val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { ACTIVITY.homeDomain.saveContactsPhoneIntoFirebase(it, user).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
            Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Obtaining the login graph from LoginActivity and instantiate
        // the @Inject fields with objects from the graph


        // Now you can access loginViewModel here and onCreateView too
        // (shared instance with the Activity and the other Fragment)
    }

    private fun createButtonClickObservable(): Observable<String> {
        // 2
        return Observable.create { emitter ->
            // 3


            // 5
            emitter.setCancellable {
                // 6
              //
            }
        }
    }
}