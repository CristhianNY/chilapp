package com.cristhianbonilla.com.artistasamerica.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.artistasamerica.App
import com.cristhianbonilla.com.artistasamerica.R
import com.cristhianbonilla.com.artistasamerica.domain.comments.CommentsDomain
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.artistasamerica.domain.contrats.comments.RecyclerCommentsPostListener
import com.cristhianbonilla.com.artistasamerica.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.artistasamerica.domain.dtos.UserDto
import com.cristhianbonilla.com.artistasamerica.ui.fragments.base.BaseDialogFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject


class CommentsDialogFragment : BaseDialogFragment() , ListenerCommentsActivity, RecyclerCommentsPostListener {


    private lateinit var commentsRecyclerView: RecyclerView

    @Inject
    lateinit var commentsDomain: CommentsDomain

    lateinit var btnSaveCommentPost : Button
    lateinit var editCommentPost : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.instance.getComponent().inject(this)
        this.setStyle(STYLE_NORMAL, R.style.DialogFragmentTheme)

        val view = inflater.inflate(R.layout.fragment_coments_fragment_dialog, container, false)

        initViews(view)

        commentsRecyclerView = view?.findViewById(R.id.coments_fragment_recyclerView) as RecyclerView
        val idSecrePost = getArguments()?.getString("idSecretPost")

        getComments(commentsRecyclerView , CommentsPostAdapter(this,commentsRecyclerView) , idSecrePost)


        btnSaveCommentPost.setOnClickListener(View.OnClickListener {

            saveCommentPost(editCommentPost.text.toString(),idSecrePost)
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes!!.windowAnimations = R.style.DialogAnimation
    }

    private fun initViews(root: View?){

        btnSaveCommentPost = root?.findViewById(R.id.btn_send_comment) as Button
        editCommentPost = root?.findViewById(R.id.content_comment) as EditText

    }

    private fun saveCommentPost(commentPost: String , idSecretPost:String?) {

        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

        Observable.just(activity?.let { saveCommentPost(user,commentPost , idSecretPost).subscribeOn(
            Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({  }, { throwable ->
            Toast.makeText(context, "Update error: ${throwable.message}", Toast.LENGTH_LONG).show()
        }) } )

        editCommentPost.text.clear()
    }

    private fun saveCommentPost(user: UserDto?, messageWhatareYouThinking: String, idSecretPost:String?)  : Completable{

        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        commentsDomain.saveCommentPost(ACTIVITY,messageWhatareYouThinking,user,idSecretPost)
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

    private  fun getComments(
       root: RecyclerView?,
       commentsPostAdapter: CommentsPostAdapter,
       idSecretPost: String?){

       val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

       Observable.just(activity?.let { getComment(user, root , commentsPostAdapter , idSecretPost).subscribeOn(
           Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({}, { throwable ->
           Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
       }) } )

   }
    private fun getComment(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter,
        idSecretPost: String?
    ) : Completable {

        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                       commentsDomain.getCommentsPost(user,root,commentsPostAdapter,idSecretPost)
                       // dashBoardDomain.getSecretsPost(user, root, secretPostRvAdapter)
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


    override fun onReadCommentsPost(
        secreCommentsPost: ArrayList<CommentPostDto>,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) {

        var linearLayoutManager = LinearLayoutManager(activity)
        var adapter = commentsPostAdapter

        root?.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))

        root?.layoutManager = linearLayoutManager
        root?.adapter = adapter

        val recyclerViewState = root?.layoutManager?.onSaveInstanceState()
        commentsPostAdapter.submitList(secreCommentsPost)

        root?.layoutManager?.onRestoreInstanceState(recyclerViewState)

    }



    override fun itemCliekc(view: View, position: Int, comment: CommentPostDto) {

    }

    override fun positionListener(view: RecyclerView, position: Int) {

    }

}
