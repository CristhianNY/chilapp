package com.cristhianbonilla.com.chilapp.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.profile.ProfileDomain
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ProfileFragment : BaseFragment() , ProfileFragmentListerner {

    @Inject
    lateinit var profileDomain:  ProfileDomain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        App.instance.getComponent().inject(this)

        getUserInformation()
        return root
    }

    private fun getUserInformation(){
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { getRemoteUserInformation(user).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({}, { throwable ->
            Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )

    }

    private  fun getRemoteUserInformation(user: UserDto?): Completable{
        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        profileDomain.getUserInformation(activity!!, user)
                    }
                }
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
    override fun onUserInformatinRead(userDto: UserDto?) {
        Toast.makeText(App.instance.applicationContext,"${userDto?.name}", Toast.LENGTH_LONG).show()
    }
}