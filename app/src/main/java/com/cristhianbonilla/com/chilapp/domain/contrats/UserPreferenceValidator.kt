package com.cristhianbonilla.com.chilapp.domain.contrats


class UserPreferenceValidator :
    ValidatorUserPreferenceInterface {

    override fun validateIfUserPrefereceIsSaved(userName:String): Boolean {

        if(userName == null) {
            return false
        }
        return true
    }
}