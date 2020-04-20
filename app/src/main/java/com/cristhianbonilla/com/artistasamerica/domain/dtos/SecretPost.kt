package com.cristhianbonilla.com.artistasamerica.domain.dtos

data class  SecretPost(var message:String,
                       var owner:String,
                       var id:String? ,
                       var likes:Int,
                       var color:String,
                       var comments:Int,
                       var imgaeUrl:String,
                       var videoUrl:String,
                       var telefono:String,
                       var category:String){

    constructor() : this("","","",0,"",0,"","","","")
}
