package com.cristhianbonilla.com.artistasamerica.ui.fragments.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.vvalidator.util.hide
import com.afollestad.vvalidator.util.show
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.contrats.dashboard.ListenerActivity
import com.cristhianbonilla.com.artistasamerica.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.artistasamerica.domain.dtos.SecretPost
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.ui.activities.meeting.ZoomMeetingActivity
import com.cristhianbonilla.com.artistasamerica.ui.fragments.addSecret.AddSecretDialogFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.base.BaseFragment
import com.cristhianbonilla.com.artistasamerica.ui.fragments.comments.CommentsDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.counter_panel.view.*
import kotlinx.android.synthetic.main.item_secret_post.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class DashboardFragment :BaseFragment(), ListenerActivity, RecyclerpostListener{
    private lateinit var secretPostRecyclerView: RecyclerView
    private lateinit var secretPostRvAdapter: SecretPostRvAdapter
    private lateinit var addNewSecretBtn: FloatingActionButton
    private lateinit var serenatasBtn: FloatingActionButton

    lateinit var progresSecretPost: ProgressBar
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

            user?.let { vm.getAllSecrets() }

            user?.let { vm.getSecretPostLikes(it) }
        }

        vm.postList.observe(this, Observer {showSecretPostInRecyclerView(it) })

        vm.postLiked.observe(this, Observer {getPostLiked(it) })

        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(secretPostRecyclerView)

        addNewSecretBtn.setOnClickListener{
            val dialog: DialogFragment? = AddSecretDialogFragment()

            dialog?.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme)
            var args: Bundle = Bundle()
            args?.putString("idSecretPost", "algo")
            dialog?.setArguments(args)
            fragmentManager?.let { dialog?.show(it, "addNewSecret") }
        }
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        @SuppressLint("RestrictedApi")
        if(!user?.phone.equals("+573157119388")){
            addNewSecretBtn.visibility = View.GONE
        }

        serenatasBtn.setOnClickListener{
            val intent = Intent(context, ZoomMeetingActivity::class.java)
            startActivity(intent)
        }

        return root
    }

   override fun onResume() {
        super.onResume()
        vm.postList.value?.count()?.let { secretPostRecyclerView.scrollToPosition(it-1) }
    }

    private fun initViews(root: View?){
        progresSecretPost = root?.findViewById(R.id.progresSecretPost) as ProgressBar
        addNewSecretBtn = root?.findViewById(R.id.add_new_secret_btn) as FloatingActionButton
        serenatasBtn = root?.findViewById(R.id.serenata_btn) as FloatingActionButton
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
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        secretPostRecyclerView?.layoutManager = linearLayoutManager
        secretPostRecyclerView?.adapter = adapter
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
        val bandPost :ImageView = itemView.image_band
        val likesImageView : ImageView = itemView.likesImageView
        val disLikesImageView : ImageView = itemView.dislike
        val numOfLikes : TextView = itemView.num_of_likes
        val progresLikes : ProgressBar = itemView.progresLikes
        val numOfComments : TextView = itemView.num_of_comments
        val whatsappIcon : ImageView = itemView.whatappImagen
        val videoCall : LinearLayout = itemView.camContainer
        val youtubeVideo : LinearLayout = itemView.youtube_video

        progresLikes.hide()

        progresSecretPost.hide()

        youtubeVideo.setOnClickListener{
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(secretPost.videoUrl)
                )
            )
        }
        videoCall.setOnClickListener{
            val intent = Intent(context, ZoomMeetingActivity::class.java)
            startActivity(intent)
        }
        whatsappIcon.setOnClickListener{
            val pm: PackageManager = context!!.packageManager
            try {
                val url = "https://api.whatsapp.com/send?phone=${secretPost.telefono}"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                startActivity(Intent.createChooser(i, "Share with"))
            } catch (e: PackageManager.NameNotFoundException) {
                Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
     //   container.setBackgroundColor(Color.parseColor(secretPost.color))

        numOfComments.text = secretPost.comments.toString()

        context?.let { dashBoardDomain.saveAnimationPreference(true, it) }

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
        val picasso = Picasso.get()
        picasso.load(secretPost.imgaeUrl)
            .into(bandPost, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                }
                override fun onError(e: Exception?) {
                }
            })
        numOfLikes.text  = secretPost.likes.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        context?.let { dashBoardDomain.deleteAnimationPreference(it) }
    }
}