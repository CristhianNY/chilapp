package com.cristhianbonilla.com.chilapp.ui.fragments.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.cristhianbonilla.com.chilapp.R


class CommentsDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        //Find the +1 button
//  mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
        this.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogFragmentTheme)
        return inflater.inflate(R.layout.fragment_coments_fragment_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.attributes!!.windowAnimations = R.style.DialogAnimation
    }

}
