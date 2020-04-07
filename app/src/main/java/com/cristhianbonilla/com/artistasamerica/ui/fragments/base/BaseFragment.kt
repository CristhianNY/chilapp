package com.cristhianbonilla.com.artistasamerica.ui.fragments.base

import android.os.Bundle
import androidx.fragment.app.Fragment

import com.cristhianbonilla.com.artistasamerica.ui.activities.MainActivity

open class BaseFragment : Fragment(){

    protected lateinit var ACTIVITY: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ACTIVITY = context as MainActivity

    }
}
