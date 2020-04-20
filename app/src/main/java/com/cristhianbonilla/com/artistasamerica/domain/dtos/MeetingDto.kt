package com.cristhianbonilla.com.artistasamerica.domain.dtos

data class MeetingDto(
    var userId: String,
    var title: String,
    var idEventos: String,
    var password: String,
    var date: String,
    var duration: String
) {

    constructor() : this("", "", "", "", "","")
}
