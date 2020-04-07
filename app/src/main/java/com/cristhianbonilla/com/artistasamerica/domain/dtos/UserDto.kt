package com.cristhianbonilla.com.artistasamerica.domain.dtos

data class UserDto(var name : String, var lastName: String, var email: String, var phone:String, var userId:String,var type:String
){

    constructor() : this("","","","","","")

}