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
    import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
    import com.cristhianbonilla.com.domain.dtos.UserDto
    import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.HomeDomain
    import com.cristhianbonilla.com.domain.repositories.login.repositories.features.login.LoginDomain
    import io.reactivex.Completable
    import io.reactivex.Observable
    import io.reactivex.android.schedulers.AndroidSchedulers
    import io.reactivex.schedulers.Schedulers
    import java.lang.Exception
    import javax.inject.Inject

    class HomeFragment : BaseFragment() {

        private lateinit var homeViewModel: HomeViewModel

        private var permissions = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

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


            if(this.activity?.let { ACTIVITY.checkPermissions(it,permissions) }!!){

                Toast.makeText(context,"Hola si tiene ",Toast.LENGTH_SHORT).show()
                registersSaveContactsToFirebase()
            }else{
                Toast.makeText(context,"No tiene  ",Toast.LENGTH_SHORT).show()
            }

            return root
        }

       private fun registersSaveContactsToFirebase(){

           val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

            Observable.just(activity?.let { saveContactsPhoneIntoFirebase(user).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
                Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
            }) } )
        }

        private fun saveContactsPhoneIntoFirebase(user: UserDto?) : Completable {

            return Completable.create { emitter ->

                try {
                    activity?.let { ACTIVITY.homeDomain.saveContactsPhoneIntoFirebase(it, user) }
                    if(emitter != null && !emitter.isDisposed){
                        emitter?.onComplete()
                    }
                }catch (e: Exception){
                    if (emitter != null && !emitter.isDisposed) {
                        emitter?.onError(e)
                    }
                }
            }
        }

    }