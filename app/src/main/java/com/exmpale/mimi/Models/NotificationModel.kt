package com.exmpale.mimi.Models

class NotificationModel {

    var likedWhoProfile: String? = null
    var likedWhoUser: String? = null
    var message: String? = null
    var currentTime: String? = null

    constructor()

    constructor(

        likedWhoProfile: String?,
        likedWhoUser: String?,
        message: String?,
        currentTime: String?,

        ) {

        this.likedWhoProfile = likedWhoProfile
        this.likedWhoUser = likedWhoUser
        this.message = message
        this.currentTime = message

    }
}