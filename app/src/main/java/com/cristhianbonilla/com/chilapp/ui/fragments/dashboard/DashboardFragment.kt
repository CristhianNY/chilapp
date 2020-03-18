package com.cristhianbonilla.com.chilapp.ui.fragments.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.vvalidator.util.hide
import com.afollestad.vvalidator.util.show
import com.airbnb.lottie.LottieAnimationView
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.SecretPost
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseFragment
import com.cristhianbonilla.com.chilapp.ui.fragments.comments.CommentsDialogFragment
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import kotlinx.android.synthetic.main.counter_panel.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.item_secret_post.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class DashboardFragment :BaseFragment(), ListenerActivity, RecyclerpostListener{

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var secretPostRecyclerView: RecyclerView
    private lateinit var secretPostRvAdapter: SecretPostRvAdapter
    lateinit var btnSendSecretPost : Button
    lateinit var changColorImage : ImageView
    lateinit var editWhatAreYouThinking : EditText
    private lateinit var animationSwipe: LottieAnimationView
    var colorPost:String ="#616161"
    var boolean:Boolean = true
    var isLiked:Boolean = false
    var counterToScroll:Int = 0
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

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        vm = ViewModelProviders.of(this, viewModelFactory)[DashBoardDomain::class.java]


        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

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
            if(editWhatAreYouThinking.text.toString().isNullOrEmpty()){

                Toast.makeText(context,"Porfavor ingresa un secreto", Toast.LENGTH_LONG).show()
            }else{

                var contentToSearch : String  = editWhatAreYouThinking.text.toString()
                editWhatAreYouThinking.text.clear()

                CoroutineScope(IO).launch {

                    saveSecretPostToFirebaseStore(contentToSearch)

                }
            }

        })

        changColorImage.setOnClickListener {
            context?.let { it1 ->
                MaterialColorPickerDialog
                    .Builder(it1)        			// Pass Activity Instance
                    .setTitle("Color")               // Change Dialog Title
                    .setColorListener { color, colorHex ->
                        colorPost = colorHex
                        editWhatAreYouThinking.setBackgroundColor(Color.parseColor(colorHex))
                    }
                    .show()
            }
        }

        animationSwipe.setOnClickListener {
            animationSwipe.visibility = View.INVISIBLE
        }

        return root
    }

    private fun initViews(root: View?){
        btnSendSecretPost = root?.findViewById(R.id.btn_send_post) as Button
        changColorImage = root?.findViewById(R.id.changColor) as ImageView
        editWhatAreYouThinking = root?.findViewById(R.id.edit_what_are_you_thinkgin) as EditText
        animationSwipe = root?.findViewById(R.id.lottie_animation_swipe) as LottieAnimationView
    }

    private  fun showSecretPostInRecyclerView(secretpostArrayList: List<SecretPost>){


        if(this.counterToScroll == secretpostArrayList.size-1){
            secretPostRecyclerView?.scrollToPosition(secretpostArrayList.size- 1)
        }
        counterToScroll++
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
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        secretPostRecyclerView?.layoutManager = linearLayoutManager
        secretPostRecyclerView?.adapter = adapter
    }
    private suspend fun saveSecretPostToFirebaseStore(messageWhatareYouThinking: String){
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }
        user?.let { dashBoardDomain.saveSecretPost(ACTIVITY,messageWhatareYouThinking, it, colorPost) }
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
        fragmentManager?.let { dialog?.show(it, "Comments") }

    }

    override fun btnLike(itemView: View, position: Int, secretPost: SecretPost) {
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        val likesImageView : ImageView = itemView.likesImageView
        val progresLikes : ProgressBar = itemView.progresLikes

        progresLikes.show()

        val disLike : ImageView = itemView.dislike

        likesImageView.visibility = View.INVISIBLE

        disLike.visibility = View.VISIBLE

        CoroutineScope(IO).launch {
            LikePost(user, secretPost)
        }
    }

    override fun btnDisLike(view: View, position: Int, secretPost: SecretPost) {
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        val likeImageView : ImageView = view.likesImageView
        val disLikeImage : ImageView = view.dislike
        val progresLikes : ProgressBar = view.progresLikes

        progresLikes.show()
        likeImageView.visibility = View.VISIBLE
        disLikeImage.visibility = View.INVISIBLE
        CoroutineScope(IO).launch {
        dashBoardDomain.makeDislike(secretPost, App.instance.applicationContext, user!!)
        }
    }

    private suspend fun LikePost(
        user: UserDto?,
        secretPost: SecretPost
    ) {

        user?.let { vm.getSecretPostLikes(it) }

            if (!postLikeds.contains(secretPost)) {
                dashBoardDomain.makeLike(secretPost, App.instance.applicationContext, user!!)
            } else {
                postIsAlreadyLiked(secretPost,user)
            }

    }

       fun postIsAlreadyLiked(
          secretPost: SecretPost,
          user: UserDto?
      ) {
       //   dashBoardDomain.makeDislike(secretPost, App.instance.applicationContext, user!!)
    }

    override fun positionListener(view: RecyclerView, position: Int) {

    }

    override fun printElement(secretPost: SecretPost, position: Int, itemView:View) {
        val ownerAnonymous :TextView = itemView.owner_anonymous
        val secretPostMessage :TextView = itemView.secret_post_message
        val likesImageView : ImageView = itemView.likesImageView
        val disLikesImageView : ImageView = itemView.dislike
        val numOfLikes : TextView = itemView.num_of_likes
        val progresLikes : ProgressBar = itemView.progresLikes
        val card : CardView = itemView.card_view

        progresLikes.hide()

        context?.let { if (dashBoardDomain.getAnimationPreference(it)){

            animationSwipe.visibility = View.INVISIBLE

        }else{
            animationSwipe.visibility = View.VISIBLE
        }

        }

        context?.let { dashBoardDomain.saveAnimationPreference(true, it) }



        card.setBackgroundColor(Color.parseColor(secretPost.color))


       var isLiked =  postLikeds.any {
            it.id == secretPost.id
        }

        if(isLiked){
             likesImageView.visibility = View.INVISIBLE
            disLikesImageView.visibility = View.VISIBLE
        }else{
            likesImageView.visibility = View.VISIBLE
            disLikesImageView.visibility = View.INVISIBLE
        }
        ownerAnonymous.text = ""
        secretPostMessage.text = secretPost.message
        numOfLikes.text  = secretPost.likes.toString()
    }

    override fun onDestroy() {
        super.onDestroy()

        context?.let { dashBoardDomain.deleteAnimationPreference(it) }
    }
}