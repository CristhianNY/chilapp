package com.cristhianbonilla.com.chilapp.domain.dtos

data class  SecretPost(var message:String,
                       var owner:String,
                       var id:String? ,
                       var likes:Int,
                       var color:String,
                        var comments:Int){

    constructor() : this("","","",0,"",0)
}

