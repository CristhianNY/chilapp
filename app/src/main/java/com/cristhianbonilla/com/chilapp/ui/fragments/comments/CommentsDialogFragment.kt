package com.cristhianbonilla.com.chilapp.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.ListenerCommentsActivity
import com.cristhianbonilla.com.chilapp.domain.contrats.comments.RecyclerCommentsPostListener
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.domain.dtos.CommentPostDto
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseDialogFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CommentsDialogFragment : BaseDialogFragment() , ListenerCommentsActivity, RecyclerCommentsPostListener {


    private lateinit var commentsRecyclerView: RecyclerView

    @Inject
    lateinit var dashBoardDomain: DashBoardDomain


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.setStyle(STYLE_NORMAL, R.style.DialogFragmentTheme)

        val view = inflater.inflate(R.layout.fragment_coments_fragment_dialog, container, false)

        commentsRecyclerView = view?.findViewById(R.id.coments_fragment_recyclerView) as RecyclerView

        getComments(commentsRecyclerView , CommentsPostAdapter(this,commentsRecyclerView))

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.attributes!!.windowAnimations = R.style.DialogAnimation
    }

   private  fun getComments(
       root: RecyclerView?,
       commentsPostAdapter: CommentsPostAdapter){

       val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }

       Observable.just(activity?.let { getComment(user, root , commentsPostAdapter).subscribeOn(
           Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe({}, { throwable ->
           Toast.makeText(context, "Error Al traer Datos: ${throwable.message}", Toast.LENGTH_LONG).show()
       }) } )

   }
    private fun getComment(
        user: UserDto?,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) : Completable {

        return Completable.create { emitter ->

            try {
                activity?.let {
                    if (user != null) {
                        getCommentsFake(commentsPostAdapter , root)
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


    fun getCommentsFake(
        commentsPostAdapter: CommentsPostAdapter,
        root: RecyclerView?
    ){

        var linearLayoutManager = LinearLayoutManager(activity)
        var adapter = commentsPostAdapter

        linearLayoutManager.reverseLayout = true
        root?.layoutManager = linearLayoutManager
        root?.adapter = adapter

        val recyclerViewState = root?.layoutManager?.onSaveInstanceState()

        root?.layoutManager?.onRestoreInstanceState(recyclerViewState)
        commentsPostAdapter.submitList(loadFake())
    }
    fun loadFake() : ArrayList<CommentPostDto>{
        val comments = ArrayList<CommentPostDto>()

        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "asdfasdfasdfieiieedasdfasdfasd"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))
        comments.add(CommentPostDto(owner = "asdfasdfa",comment = "asdfasdfasd",image = "sdafsdfasdfasdfasdasdfasdf"))

        return comments
    }

    override fun onReadCommentsPost(
        secreCommentsPost: ArrayList<CommentPostDto>,
        root: RecyclerView?,
        commentsPostAdapter: CommentsPostAdapter
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemCliekc(view: View, position: Int, comment: CommentPostDto) {

    }

    override fun positionListener(view: RecyclerView, position: Int) {

    }

}
