package com.cristhianbonilla.com.chilapp.ui.fragments.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.cristhianbonilla.com.chilapp.ui.activities.MainActivity

open class BaseDialogFragment: DialogFragment() {

        protected lateinit var ACTIVITY: MainActivity

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            ACTIVITY = context as MainActivity

        }


}