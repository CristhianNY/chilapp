package com.cristhianbonilla.com.chilapp.domain.dtos

import android.graphics.Bitmap

data class CommentPostDto(var comment:String,var owner:String,var image:String,var idComment:String){

    constructor() : this("","","","")
}