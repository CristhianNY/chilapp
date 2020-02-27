    package com.cristhianbonilla.com.chilapp.ui.fragments.home

    import android.Manifest
    import android.content.Context
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.cristhianbonilla.com.chilapp.App
    import com.cristhianbonilla.com.chilapp.R
    import com.cristhianbonilla.com.chilapp.domain.contrats.home.ListenerHomeFragment
    import com.cristhianbonilla.com.chilapp.domain.dtos.ContactDto
    import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
    import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
    import io.reactivex.Completable
    import io.reactivex.Observable
    import io.reactivex.android.schedulers.AndroidSchedulers
    import io.reactivex.schedulers.Schedulers
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
                registersSaveContactsToFirebase(friendsRecyclerView,FriendsAdapterRecyclerView(this,friendsRecyclerView))
                getFriends(friendsRecyclerView,FriendsAdapterRecyclerView(this,friendsRecyclerView))
            }else{
                Toast.makeText(context,"No tiene  ",Toast.LENGTH_SHORT).show()
            }

            return root
        }

        private fun getFriends(friendsRecyclerView: RecyclerView, friendsAdapterRecyclerView: FriendsAdapterRecyclerView){
            Observable.fromCallable {   ACTIVITY.homeDomain.getFriends(App.instance.applicationContext,friendsRecyclerView,friendsAdapterRecyclerView) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ orderItem ->
                    print(orderItem)
                }, { e ->
                    print("nada")
                }, {
                    showFriends(App.instance.applicationContext,friendsRecyclerView,friendsAdapterRecyclerView)
                    print("completado")
                })
        }
        private fun registersSaveContactsToFirebase(friendsRecyclerView: RecyclerView, friendsAdapterRecyclerView: FriendsAdapterRecyclerView){

           val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }
           Observable.just(activity?.let { saveContactsPhoneIntoFirebase(user).subscribeOn(
                Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({}, { throwable ->
                Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
            }) })
        }


        private fun showFriends(
            applicationContext: Context,
            friendsRecyclerView: RecyclerView,
            friendsAdapterRecyclerView: FriendsAdapterRecyclerView
        ) {
            var linearLayoutManager = LinearLayoutManager(activity)
            var adapter = friendsAdapterRecyclerView
            linearLayoutManager.reverseLayout = true


            friendsRecyclerView?.layoutManager = linearLayoutManager
            friendsRecyclerView?.adapter = adapter

            friendsAdapterRecyclerView.notifyDataSetChanged()

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
         print("Cristhian$position")
        }

        override fun onFriensdRead(
            contacts: List<ContactDto>,
            friendsAdapterRecyclerView: FriendsAdapterRecyclerView
        ) {
            friendsAdapterRecyclerView.submitList(contacts)

        }

    }
