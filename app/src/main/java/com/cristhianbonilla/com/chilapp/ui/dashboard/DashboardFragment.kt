package com.cristhianbonilla.com.chilapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cristhianbonilla.com.chilapp.R
import com.cristhianbonilla.com.domain.repositories.login.repositories.features.home.HomeDomain
import javax.inject.Inject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    @Inject
    lateinit var homeDomain: HomeDomain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}