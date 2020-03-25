package com.cristhianbonilla.com.chilapp.ui.fragments.addSecret

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.chilapp.ui.fragments.base.BaseDialogFragment

class AddSecretDialogFragment : BaseDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        val view = inflater.inflate(R.layout.fragment_add_fragment, container, false)


        return view
    }

    private fun initViews(root: View?){



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes!!.windowAnimations = R.style.DialogAnimation
    }
}