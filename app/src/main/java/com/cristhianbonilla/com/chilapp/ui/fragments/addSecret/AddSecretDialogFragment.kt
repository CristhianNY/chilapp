package com.cristhianbonilla.com.chilapp.ui.fragments.addSecret

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Scroller
import com.cristhianbonilla.com.chilapp.App
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.domain.dashboard.DashBoardDomain
import com.cristhianbonilla.com.chilapp.ui.customviews.ResizeEditText
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseDialogFragment
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddSecretDialogFragment : BaseDialogFragment() {


    @Inject
    lateinit var dashBoardDomain: DashBoardDomain


    lateinit var addNewSecreEditText:ResizeEditText
    lateinit var colorImageIcon: ImageView
    lateinit var btnSendSecret: ImageView
    var colorHexadecimal:String = "#6f00ff"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        App.instance.getComponent().inject(this)
        val view = inflater.inflate(R.layout.fragment_add_fragment, container, false)

        initViews(view)

        addNewSecreEditText.isEnabled = true;
        addNewSecreEditText.isFocusableInTouchMode = true;
        addNewSecreEditText.isFocusable = true;
        addNewSecreEditText.setEnableSizeCache(false);
        addNewSecreEditText.movementMethod = null;
        addNewSecreEditText.maxHeight = 330


         colorImageIcon.setOnClickListener{

             activity?.let { it1 ->
                 MaterialColorPickerDialog
                     .Builder(it1)       			// Pass Activity Instance
                     .setColors(							// Pass Predefined Hex Color
                         arrayListOf(
                             "#f6e58d", "#ffbe76", "#ff7979", "#badc58", "#dff9fb",
                             "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"
                         )
                     ).setTitle("Seleccionar Color")
                     .setColorListener { color, colorHex ->
                         // Handle Color Selection
                         colorHexadecimal = colorHex
                         addNewSecreEditText.setBackgroundColor(color)
                     }
                     .show()
             }
        }

        btnSendSecret.setOnClickListener{

            CoroutineScope(IO).launch {

                var contentToSearch = addNewSecreEditText.text
                saveSecretPostToFirebaseStore(contentToSearch.toString())
            }

            this.dismiss()
        }

        hideSoftKeyboard(view)

        return view
    }

    private fun initViews(root: View?){
        addNewSecreEditText = root?.findViewById(R.id.addNewSecretEditText) as ResizeEditText

        colorImageIcon = root?.findViewById(R.id.changeColor) as ImageView
        btnSendSecret = root?.findViewById(R.id.send_secret) as ImageView

        addNewSecreEditText.setScroller(Scroller(context))
        addNewSecreEditText.isVerticalScrollBarEnabled = true
        addNewSecreEditText. movementMethod = ScrollingMovementMethod()
    }


    private  fun saveSecretPostToFirebaseStore(messageWhatareYouThinking: String){
        val user =  context?.let { ACTIVITY.loginDomain.getUserPreference("userId",it) }
        user?.let { dashBoardDomain.saveSecretPost(ACTIVITY,messageWhatareYouThinking, it, colorHexadecimal) }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes!!.windowAnimations = R.style.DialogAnimation
    }

    private fun hideSoftKeyboard(root: View?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(root!!.windowToken, 0)
    }
}