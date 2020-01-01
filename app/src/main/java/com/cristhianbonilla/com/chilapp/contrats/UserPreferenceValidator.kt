package com.cristhianbonilla.com.chilapp.contrats

import android.content.Intent
import com.cristhianbonilla.com.chilapp.MainActivity
import com.cristhianbonilla.com.chilapp.ui.activities.feature.register.RegisterActivity


class UserPreferenceValidator : ValidatorUserPreferenceInterface {

    override fun validateIfUserPrefereceIsSaved(userName:String): Boolean {

        if(userName == null) {
            return false
        }
        return true
    }
}