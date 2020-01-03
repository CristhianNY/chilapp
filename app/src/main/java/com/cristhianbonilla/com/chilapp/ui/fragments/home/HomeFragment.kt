package com.cristhianbonilla.com.chilapp.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cristhianbonilla.com.chilapp.MainActivity
import com.cristhianbonilla.com.chilapp.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var ACTIVITY: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        ACTIVITY = context as MainActivity

        if(ACTIVITY.permissionIsGranted){

            Toast.makeText(context,"Hola si tiene ",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"No tiene  ",Toast.LENGTH_SHORT).show()
        }

        return root
    }
}