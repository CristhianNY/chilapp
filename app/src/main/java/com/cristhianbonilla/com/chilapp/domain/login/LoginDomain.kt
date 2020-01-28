package com.cristhianbonilla.com.chilapp.domain.login

import android.content.Context
import com.cristhianbonilla.com.chilapp.domain.dtos.UserDto
import com.cristhianbonilla.com.chilapp.domain.login.repository.LoginRepository
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class LoginDomain(loginRepository : LoginRepository){

    var loginRepository : LoginRepository = loginRepository

    fun saveUser(user: UserDto, contex:Context){

        FirebaseDatabase.getInstance().getReference("disconnectmessage").onDisconnect().setValue("I disconnected!")

        loginRepository.saveUser(user, contex)
    }

    fun getUserPreference(key:String , contex:Context) : UserDto {
       return loginRepository.getUserPreferenceDto(contex)
    }

    fun getUserNamePreference(key:String , contex:Context) : String?{
        return loginRepository.getUserNamePreference(key, contex)
    }

    fun deleteeUserPreference(context:Context){
        loginRepository.deleteUserPreference(context)
    }

     fun getAge(year: Int, month: Int, day: Int): String? {
        val dob: Calendar = Calendar.getInstance()
        val today: Calendar = Calendar.getInstance()
        dob.set(year, month, day)
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }


}
