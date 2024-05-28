package com.exmpale.mimi.Models

import com.google.firebase.firestore.FieldValue
import java.util.Date

class PostModel {


    var uid: String? = null
    var postProfileImage: String? = null
    var postProfileUser: String? = null
    var postImage: String? = null
    var postCaption: String? = null
    var postLike: Int? = null
    var PostComment: Int? = null
    var PostBookmark: Int? = null
    var postType: Boolean? = null
    var currentTime: String? = null


    constructor()


    constructor(
        uid: String?,
        postProfileImage: String?,
        postProfileUser: String?,
        postImage: String?,
        postCaption: String?,
        postLike: Int?,
        PostComment: Int?,
        PostBookmark: Int?,
        postType: Boolean?,
        currentTime: String?) {
        this.uid = uid
        this.postProfileImage = postProfileImage
        this.postProfileUser = postProfileUser
        this.postImage = postImage
        this.postCaption = postCaption
        this.postLike = postLike
        this.PostComment = PostComment
        this.PostBookmark = PostBookmark
        this.postType = postType
        this.currentTime = currentTime
    }


}