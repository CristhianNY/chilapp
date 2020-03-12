package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.base.Result
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsDialogFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DashboardFragment :BaseFragment(), ListenerActivity, RecyclerpostListener{

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var secretPostRecyclerView: RecyclerView
    private lateinit var secretPostRvAdapter: SecretPostRvAdapter
    lateinit var btnSendSecretPost : Button
    lateinit var editWhatAreYouThinking : EditText

    var isLiked:Boolean = false
    var postLikeds :ArrayList<SecretPost> = ArrayList()


    @Inject
    lateinit var dashBoardDomain:  DashBoardDomain

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

companion object{
    lateinit var vm:DashBoardDomain
}


    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.getComponent().inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        vm = ViewModelProviders.of(this, viewModelFactory)[DashBoardDomain::class.java]


        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)

        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        initViews(root)
        secretPostRecyclerView = root?.findViewById(R.id.secret_post_recyclerView) as RecyclerView

        secretPostRvAdapter = SecretPostRvAdapter(this,secretPostRecyclerView)

        setUpRecyclerView()

        CoroutineScope(IO).launch {

            delay(3000)

            val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

            user?.let { vm.getSecretPostFromFirebaseRealTIme(it) }

            user?.let { vm.getSecretPostLikes(it) }
        }

        vm.postList.observe(this, Observer {showSecretPostInRecyclerView(it) })

        vm.postLiked.observe(this, Observer {getPostLiked(it) })

        btnSendSecretPost.setOnClickListener(View.OnClickListener {

            CoroutineScope(IO).launch {
                saveSecretPostToFirebaseStore(editWhatAreYouThinking.text.toString())
                editWhatAreYouThinking.text.clear()
            }
        })

        return root
    }

    private fun initViews(root: View?){
        btnSendSecretPost = root?.findViewById(R.id.btn_send_post) as Button
        editWhatAreYouThinking = root?.findViewById(R.id.edit_what_are_you_thinkgin) as EditText
    }

    private  fun showSecretPostInRecyclerView(secretpostArrayList: List<SecretPost>){

        secretPostRvAdapter.submitList(secretpostArrayList)
        secretPostRvAdapter.notifyDataSetChanged()
    }

    private  fun getPostLiked(secretpostArrayList: List<SecretPost>){
        if(secretpostArrayList.isNotEmpty()){
            isLiked = true
            postLikeds = secretpostArrayList as ArrayList<SecretPost>
        }
    }

    private fun setUpRecyclerView(){
        var linearLayoutManager = LinearLayoutManager(activity)
        var adapter = secretPostRvAdapter
        linearLayoutManager.reverseLayout = true
        secretPostRecyclerView?.layoutManager = linearLayoutManager
        secretPostRecyclerView?.adapter = adapter
    }
    private suspend fun saveSecretPostToFirebaseStore(messageWhatareYouThinking: String){
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }
        user?.let { dashBoardDomain.saveSecretPost(ACTIVITY,messageWhatareYouThinking, it) }
    }

    override fun itemCliekc(
        view: View,
        position: Int,
        secretPost: SecretPost
    ) {
        val dialog: DialogFragment? = CommentsDialogFragment()

        dialog?.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme)
        var args: Bundle = Bundle()
        args?.putString("idSecretPost", secretPost.id)
        dialog?.setArguments(args)
        dialog?.show(fragmentManager, "Comments")

    }

    override fun btnLike(view: View, position: Int, secretPost: SecretPost) {
          val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        CoroutineScope(IO).launch {
            LikePost(user, secretPost)
        }
    }

    private suspend fun LikePost(
        user: UserDto?,
        secretPost: SecretPost
    ) {

        user?.let { vm.getSecretPostLikes(it) }

       CoroutineScope(IO).launch {

            if (!postLikeds.contains(secretPost)) {
                dashBoardDomain.makeLike(secretPost, App.instance.applicationContext, user!!)
            } else {
                postIsAlreadyLiked()
            }
        }
    }

      suspend fun postIsAlreadyLiked(){
          withContext(Main){
              Toast.makeText(App.instance.applicationContext,"Ya Diste Like",Toast.LENGTH_LONG).show()
          }
    }

    override fun positionListener(view: RecyclerView, position: Int) {

    }
}