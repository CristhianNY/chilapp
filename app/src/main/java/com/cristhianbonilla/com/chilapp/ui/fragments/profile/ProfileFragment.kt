package com.cristhianbonilla.com.chilapp.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.profile.ProfileFragmentListerner
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.profile.ProfileDomain
import com.cristhianbonilla.com.chilapp.ui.activities.login.LoginActivty
import com.cristhianbonilla.com.chilapp.ui.activities.meeting.ZoomMeetingActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ProfileFragment : BaseFragment() , ProfileFragmentListerner {

    @Inject
    lateinit var profileDomain:  ProfileDomain

    lateinit var usernameTv: TextView
    lateinit var emailTv: TextView
    lateinit var telTv: TextView
    lateinit var birth: TextView
    lateinit var tvUsernameDescription: TextView
    lateinit var logAoutImageView : ImageView
    lateinit var videoMeeting : FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        initViews(root)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        App.instance.getComponent().inject(this)

        getUserInformation(root)

        logAoutImageView.setOnClickListener{
            ACTIVITY.logOut()
        }

        videoMeeting.setOnClickListener{
            val intent = Intent(context, ZoomMeetingActivity::class.java)
            startActivity(intent)
        }
        return root


    }

    private fun initViews(root: View){
        logAoutImageView = root?.findViewById(R.id.log_out)
        videoMeeting = root?.findViewById(R.id.video_meeting)
    }
    private fun getUserInformation(root: View){
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { getRemoteUserInformation(user,root).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({}, { throwable ->
            Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )

    }

    private  fun getRemoteUserInformation(user: UserDto?,root: View): Completable{
        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        profileDomain.getUserInformation(activity!!, user,root)
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
    override fun onUserInformatinRead(
        userDto: UserDto?,
        root: View
    ) {
        usernameTv = root.findViewById(R.id.tv_username)
        emailTv = root.findViewById(R.id.tv_email)
        telTv = root.findViewById(R.id.phone_tv)
        birth = root.findViewById(R.id.birth_tv)
        tvUsernameDescription = root.findViewById(R.id.tv_username_description)

        usernameTv.text= userDto?.name
        emailTv.text = userDto?.email
        telTv.text = userDto?.phone
        birth.text = userDto?.birthDate
        tvUsernameDescription.text = userDto?.name

    }
}