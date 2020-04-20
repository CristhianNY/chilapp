package com.cristhianbonilla.com.artistasamerica.domain.dtos

data class CommentPostDto(
    var comment: String, var owner: String, var image: String, var idComment: String,
    var name: String
) {

    constructor() : this("", "", "", "", "")
}