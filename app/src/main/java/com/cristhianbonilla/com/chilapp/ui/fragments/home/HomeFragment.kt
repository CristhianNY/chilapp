    package com.cristhianbonilla.com.chilapp.ui.fragments.home

    import android.Manifest
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.view.animation.OvershootInterpolator
    import android.widget.TextView
    import android.widget.Toast
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProviders
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.cristhianbonilla.com.chilapp.App
    import com.cristhianbonilla.com.chilapp.R
    import com.cristhianbonilla.com.chilapp.domain.contrats.home.ListenerHomeFragment
    import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
    import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
    import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
    import com.cristhianbonilla.com.chilapp.ui.fragments.dashboard.SecretPostRvAdapter
    import io.reactivex.Completable
    import io.reactivex.Observable
    import io.reactivex.Single
    import io.reactivex.android.schedulers.AndroidSchedulers
    import io.reactivex.schedulers.Schedulers
    import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
    import java.lang.Exception

    class HomeFragment : BaseFragment() , RecyclerFriendListener , ListenerHomeFragment {

        lateinit var friendsRecyclerView: RecyclerView

        private var permissions = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            val root = inflater.inflate(R.layout.fragment_home, container, false)

            friendsRecyclerView =   root?.findViewById(R.id.friendsRecyclerview) as RecyclerView



            if(this.activity?.let { ACTIVITY.checkPermissions(it,permissions) }!!){

                Toast.makeText(context,"Hola si tiene ",Toast.LENGTH_SHORT).show()
                registersSaveContactsToFirebase()
            }else{
                Toast.makeText(context,"No tiene  ",Toast.LENGTH_SHORT).show()
            }

            return root
        }

        private fun getFriends(friendsRecyclerView: RecyclerView, friendsAdapterRecyclerView: FriendsAdapterRecyclerView) {

            val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

            Observable.just(activity?.let { getingFriends(user,friendsRecyclerView,friendsAdapterRecyclerView).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
                Toast.makeText(context, " error: ${throwable.message}", Toast.LENGTH_LONG).show()
            }) } )
        }

        private fun getingFriends( user: UserDto?,
                                   root: RecyclerView?,
                                   friendsAdapterRecyclerView: FriendsAdapterRecyclerView) : Completable{

            return Completable.create { emitter ->

                try {
                    activity?.let {
                        if (user != null) {
                            ACTIVITY.homeDomain.getFriends(App.instance.applicationContext,root,friendsAdapterRecyclerView)
                            //  ACTIVITY.dashBoardDomain.saveSecretPost(ACTIVITY,messageWhatareYouThinking,user)
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

        private fun registersSaveContactsToFirebase(){

           val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

            Observable.just(activity?.let { saveContactsPhoneIntoFirebase(user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({getFriends(friendsRecyclerView, FriendsAdapterRecyclerView(this,friendsRecyclerView)) }, { throwable ->
                Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
            }) } )
        }


        fun pruebas(){
            getingFriends(user,friendsRecyclerView,friendsAdapterRecyclerView).doOnSubscribe()
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

        override fun itemCliekc(view: View, position: Int, contact: ContactDto) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun positionListener(view: RecyclerView, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onFriensdRead(
            contacts: List<ContactDto>,
            root: RecyclerView?,
            friendsAdapterRecyclerView: FriendsAdapterRecyclerView
        ) {

            var linearLayoutManager = LinearLayoutManager(activity)
            var adapter = friendsAdapterRecyclerView

            root?.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))

            root?.layoutManager = linearLayoutManager
            root?.adapter = adapter

            val recyclerViewState = root?.layoutManager?.onSaveInstanceState()
            friendsAdapterRecyclerView.submitList(contacts)

            root?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        }

    }