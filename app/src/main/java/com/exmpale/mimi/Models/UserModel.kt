package com.exmpale.mimi.Models

class UserModel {

    var id: String? = null
    var name: String? = null
    var username: String? = null
    var email: String? = null
    var bio: String? = null
    var photo: String? = null
    var status: String? = null
    var statuscaption: String? = null
    var posts : Int? = null
    var follower: Int? = null
    var following: Int? = null


    constructor()
    constructor(


        id: String?,
        name: String?,
        username: String?,
        email: String?,
        bio: String?,
        photo: String?,
        status: String?,
        statuscaption: String?,
        posts: Int?,
        follower: Int?,
        following: Int?


    ) {

        this.id = id
        this.name = name
        this.username = username
        this.email = email
        this.bio = bio
        this.photo = photo
        this.status = status
        this.statuscaption = statuscaption
        this.posts = posts
        this.follower = follower
        this.following = following

    }
}
