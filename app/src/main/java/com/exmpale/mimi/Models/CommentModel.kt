package com.exmpale.mimi.Models

class CommentModel {

    var uid : String? = null
    var postId: String? = null
    var commentText: String? = null
    var userName: String? = null
    var userProfile: String? = null

    constructor()

    constructor(
        uid: String?,
        postId: String?,
        commentText: String?,
        userName: String?,
        userProfile: String?,

    ) {
        this.uid = uid
        this.postId = postId
        this.commentText = commentText
        this.userName = userName
        this.userProfile = userProfile

    }
}