package com.cristhianbonilla.com.chilapp.domain.dtos

data class  SecretPost(var message:String, var owner:String, var id:String? , var likes:Int){

    constructor() : this("","","",0)
}

