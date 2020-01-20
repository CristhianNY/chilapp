package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.home.HomeFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.home.HomeViewModel
import com.cristhianbonilla.com.domain.dtos.SecretPost
import com.cristhianbonilla.com.domain.dtos.UserDto
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.dashboard.DashBoardDomain
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.HomeDomain
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.lang.Exception
import javax.inject.Inject

class DashboardFragment :BaseFragment(){

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var secretPostRecyclerView: RecyclerView

    private lateinit var secretPostRvAdapter: SecretPostRvAdapter

    lateinit var btnSendSecretPost : Button


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


        initRecyclerViewSecretPost(root)

        initViews(root)

        loadPost()

        btnSendSecretPost.setOnClickListener(View.OnClickListener {

          saveSecretPost()

        })
        return root


    }

    private fun initViews(root: View?){

        btnSendSecretPost = root?.findViewById(R.id.btn_send_post) as Button

    }

    private fun saveSecretPost() {

        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { saveSecretPost(user).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
            Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )
    }

    private fun initRecyclerViewSecretPost(root: View?){
        secretPostRecyclerView = root?.findViewById(R.id.secret_post_recyclerView) as RecyclerView

        secretPostRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            secretPostRvAdapter = SecretPostRvAdapter()
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            secretPostRecyclerView.adapter = secretPostRvAdapter
        }

    }

    private fun saveSecretPost(user: UserDto?) : Completable {

        return Completable.create { emitter ->

            try {
                activity?.let { ACTIVITY.dashBoardDomain.saveSecretPost() }
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

    private fun loadPost(){

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

        secretPostRvAdapter.submitList(secretpostlist)
    }
}