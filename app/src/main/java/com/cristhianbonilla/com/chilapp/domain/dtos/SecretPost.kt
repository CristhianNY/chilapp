package com.cristhianbonilla.com.chilapp.domain.dtos

data class  SecretPost(var message:String, var owner:String){

    constructor() : this("","")
}

