package com.cristhianbonilla.com.chilapp.ui.fragments.addSecret

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Scroller
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.customviews.ResizeEditText
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseDialogFragment


class AddSecretDialogFragment : BaseDialogFragment() {

    lateinit var addNewSecreEditText:ResizeEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        val view = inflater.inflate(R.layout.fragment_add_fragment, container, false)

        initViews(view)

        addNewSecreEditText.isEnabled = true;
        addNewSecreEditText.isFocusableInTouchMode = true;
        addNewSecreEditText.isFocusable = true;
        addNewSecreEditText.setEnableSizeCache(false);
        addNewSecreEditText.movementMethod = null;
        addNewSecreEditText.maxHeight = 330;
        hideSoftKeyboard(view)
        return view
    }

    private fun initViews(root: View?){
        addNewSecreEditText = root?.findViewById(R.id.addNewSecretEditText) as ResizeEditText

        addNewSecreEditText.setScroller(Scroller(context))
        addNewSecreEditText.isVerticalScrollBarEnabled = true
        addNewSecreEditText. movementMethod = ScrollingMovementMethod()
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