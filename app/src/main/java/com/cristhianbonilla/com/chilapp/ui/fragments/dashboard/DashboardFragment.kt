package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.lang.Exception
import javax.inject.Inject

class DashboardFragment :BaseFragment(), ListenerActivity{

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var secretPostRecyclerView: RecyclerView

    private lateinit var secretPostRvAdapter: SecretPostRvAdapter

    lateinit var btnSendSecretPost : Button
    lateinit var editWhatAreYouThinking : EditText

    @Inject
    lateinit var dashBoardDomain:  DashBoardDomain

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        initViews(root)
        secretPostRecyclerView = root?.findViewById(R.id.secret_post_recyclerView) as RecyclerView
        callSecretPost(secretPostRecyclerView)

        btnSendSecretPost.setOnClickListener(View.OnClickListener {

          saveSecretPost(editWhatAreYouThinking.text.toString())

        })
        return root


    }

    private fun initViews(root: View?){

        btnSendSecretPost = root?.findViewById(R.id.btn_send_post) as Button
        editWhatAreYouThinking = root?.findViewById(R.id.edit_what_are_you_thinkgin) as EditText

    }

    private fun saveSecretPost(messageWhatareYouThinking: String) {

        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { saveSecretPost(user,messageWhatareYouThinking).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
            Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )

        editWhatAreYouThinking.text.clear()
    }

    private fun initRecyclerViewSecretPost(root: View? , secretpostlist: ArrayList<SecretPost>){


        secretPostRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            secretPostRvAdapter = SecretPostRvAdapter()
            secretPostRvAdapter.submitList(secretpostlist)

            secretPostRvAdapter.notifyDataSetChanged()
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            secretPostRecyclerView.adapter = secretPostRvAdapter
        }

    }

    private fun saveSecretPost(user: UserDto?, messageWhatareYouThinking: String) : Completable {

        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        dashBoardDomain.saveSecretPost(ACTIVITY,messageWhatareYouThinking,user)
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

    private fun loadPost():ArrayList<SecretPost>{

        val secretpostlist = ArrayList<SecretPost>()
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )
        secretpostlist.add(
            SecretPost(
                owner = "Usuario Anonimo",
                message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum"
            )
        )


        return secretpostlist
    }

    private fun callDomainService() {
        val secretpostlist = ArrayList<SecretPost>()
        val list = loadPost()

        list.toObservable()

            .subscribeBy(

                onNext = {
                    secretpostlist.add(it)
                    secretPostRvAdapter.submitList(secretpostlist)

                    secretPostRvAdapter.notifyDataSetChanged()
                },
                onError = { it.printStackTrace() },
                onComplete = { println("onComplete!") }

            )
    }

    private fun callSecretPost(root: RecyclerView?) {

        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { getSecrePost(user, root).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({ testing() }, { throwable ->
            Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )
    }
    private fun getSecrePost(
        user: UserDto?,
        root: RecyclerView?
    ) : Completable {

        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        dashBoardDomain.getSecretsPost(user, root)
                      //  ACTIVITY.dashBoardDomain.getSecretsPost(user)
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


    fun testing(){

        print("hola")
    }

    override fun onSecretPostRead(secretpostArrayList: ArrayList<SecretPost>, root: RecyclerView?) {
        secretPostRvAdapter = SecretPostRvAdapter()
        root?.layoutManager = LinearLayoutManager(activity)
        root?.adapter = this.secretPostRvAdapter
        secretPostRvAdapter.submitList(secretpostArrayList)
        secretPostRvAdapter.notifyDataSetChanged()


    }
}