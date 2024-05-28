package com.exmpale.mimi

import android.net.Uri
import com.exmpale.mimi.Models.CommentModel
import com.exmpale.mimi.Models.NotificationModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.utils.Approved_Post
import com.exmpale.mimi.utils.Favourite
import com.exmpale.mimi.utils.Follow
import com.exmpale.mimi.utils.Liked_Post_Notification
import com.exmpale.mimi.utils.Liked_User_Details
import com.exmpale.mimi.utils.Post_user
import com.exmpale.mimi.utils.User_Node
import com.exmpale.mimi.utils.User_Post_Comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import javax.security.auth.login.LoginException

class UserRepository {


    //Registration Work

    val auth = FirebaseAuth.getInstance()


    var postsCount: Int? = null
    var postProfileImage: String? = null
    var postProfileUser: String? = null


    suspend fun registerUser(
        userId: String,
        name: String,
        username: String,
        email: String,
        password: String,
        bio: String,
        Photo: String,
        status: String,
        statuscaption: String,
        posts: Int,
        follower: Int,
        following: Int
    ): Boolean {


        return try {

//            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
//                val userId = it.user!!.uid
            val userData = UserModel(
                userId,
                name,
                username,
                email,
                bio,
                Photo,
                status,
                statuscaption,
                posts,
                follower,
                following
            )
            Firebase.firestore.collection(User_Node)
                .document(FirebaseAuth.getInstance().currentUser!!.uid).set(userData)
                .addOnSuccessListener {

                }

//            }

            true

        } catch (e: LoginException) {

            false

        }


    }


    suspend fun fetchPosts(): List<PostModel> {

        val snapshot = Firebase.firestore.collection(Approved_Post)
            .orderBy("currentTime", Query.Direction.ASCENDING)
            .get().await()
        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }


    }

    suspend fun fetchBookmarkedPosts(): List<PostModel> {

        val snapshot =
            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite)
                .orderBy("currentTime", Query.Direction.ASCENDING)
                .get().await()
        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }


    }

    suspend fun fetchSelectedUserPosts(Uid: String): List<PostModel> {

        val snapshot = Firebase.firestore.collection(Uid + Post_user).get().await()
        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }


    }


    suspend fun fetchPostsPhoto(): List<PostModel> {

        val snapshot = Firebase.firestore.collection(Approved_Post)
            .whereEqualTo("postType", false)
            .orderBy("currentTime", Query.Direction.ASCENDING)
            .get().await()
        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }


    }


    suspend fun fetchUserData(userId: String): UserModel? {

        val snapshot = Firebase.firestore.collection(User_Node).document(userId).get().await()
        return snapshot.toObject<UserModel>()

    }

    suspend fun fetchFollowingData(userId: String): List<UserModel> {

        val snapshot = Firebase.firestore.collection("$userId$Follow").get().await()
        return snapshot.documents.mapNotNull { it.toObject<UserModel>() }

    }


    suspend fun updateFollowing(following: Int): Int {

        val updates = hashMapOf<String, Any>()

        updates["following"] = following


        Firebase.firestore.collection(User_Node).document(Firebase.auth.currentUser!!.uid).update(
            updates as Map<String, Any>
        ).await()
        return following

    }

    suspend fun updateFollower(uid: String, follower: Int): Int {

        val updates = hashMapOf<String, Any>()

        updates["follower"] = follower


        Firebase.firestore.collection(User_Node).document(uid).update(
            updates as Map<String, Any>
        ).await()
        return follower

    }


    suspend fun SaveUserPosts(item: PostModel): Boolean {


        return try {


            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite)
                .document()
                .set(item).await()
            true
        } catch (e: java.lang.Exception) {
            false
        }

    }


    suspend fun fetchPostComment(postId: String): List<CommentModel> {

        val snapshot = Firebase.firestore.collection(Approved_Post).document(postId).collection(
            User_Post_Comment
        ).get().await()
        return snapshot.toObjects<CommentModel>()

    }

    suspend fun fetchLikedUserInformation(userUid: String): List<NotificationModel> {

        val snapshot =
            Firebase.firestore.collection(Liked_Post_Notification).document(userUid).collection(
                Liked_User_Details
            )
                .orderBy("currentTime", Query.Direction.DESCENDING)
                .get().await()

        return snapshot.toObjects<NotificationModel>()

    }


    suspend fun upLoadPostIntoFirebase(
        postImage: String,
        postCaption: String,
        postLike: Int,
        PostComment: Int,
        PostBookmark: Int,
        postType: Boolean,
        currentTime: String
    ): Boolean {

        return try {


            val userPost = PostModel(
                FirebaseAuth.getInstance().currentUser!!.uid,
                postProfileImage,
                postProfileUser,
                postImage,
                postCaption,
                postLike,
                PostComment,
                PostBookmark,
                postType,
                currentTime
            )
            Firebase.firestore.collection(Post_user).document().set(userPost).await()
            postsCount = postsCount?.plus(1)
            Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Post_user)
                .document().set(userPost).await()
            val updates = hashMapOf<String, Any>(
                "posts" to postsCount!!
            )

            Firebase.firestore.collection(User_Node)
                .document(Firebase.auth.currentUser!!.uid).update(
                    updates as Map<String, Any>
                ).await()
            true
        } catch (e: java.lang.Exception) {
            false
        }

    }


    suspend fun upLoadRealsIntoFirebase(realsVideo: String, realsCaption: String): Boolean {


        return try {


            val updates = hashMapOf<String, Any>(
                "status" to realsVideo,
                "statuscaption" to realsCaption
            )

            Firebase.firestore.collection(User_Node)
                .document(Firebase.auth.currentUser!!.uid).update(
                    updates as Map<String, Any>
                ).await()
            true
        } catch (e: Exception) {
            false
        }

    }


    suspend fun publishLikedUserInformation(uid: String, message: String): Boolean {

        return try {

            val snapshot = Firebase.firestore.collection(User_Node)
                .document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
            val tempList = snapshot.toObject<UserModel>()
            if (tempList != null) {
                postProfileImage = tempList.photo
            }
            if (tempList != null) {
                postProfileUser = tempList.name
            }

            val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")

            val currentDateAndTime = sdf.format(Date())

            val userNotification =
                NotificationModel(postProfileImage, postProfileUser, message, currentDateAndTime)
            Firebase.firestore.collection(Liked_Post_Notification).document(uid).collection(
                Liked_User_Details
            ).document().set(userNotification).await()
            true
        } catch (e: java.lang.Exception) {
            false
        }


    }


    suspend fun fetchUserPostsCount(callBack: (Int?) -> Unit) {


        val snapshot = Firebase.firestore.collection(User_Node)
            .document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
        val tempList = snapshot.toObject<UserModel>()
        postsCount = tempList!!.posts
        postProfileImage = tempList.photo
        postProfileUser = tempList.name
        callBack(postsCount)


    }


    suspend fun fetchUserPostData(userId: String): List<PostModel> {

        val snapshot = Firebase.firestore
            .collection("$userId$Post_user")
            .whereEqualTo("postType", false)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }

    }

    suspend fun fetchUserPostText(userId: String): List<PostModel> {

        val snapshot = Firebase.firestore
            .collection("$userId$Post_user")
            .whereEqualTo("postType", true)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject<PostModel>() }

    }


    suspend fun updateLike(like: Int, documentId: String): Int {

        val updates = hashMapOf<String, Any>()

        updates["postLike"] = like

        Firebase.firestore.collection(Approved_Post).document(documentId).update(
            updates as Map<String, Any>
        ).await()
        return like

    }

    suspend fun updateUserData(
        name: String,
        username: String,
        photo: String,
        bio: String,
        selectedImage: Uri
    ): Boolean {


        return try {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {


                val updatesSecond = hashMapOf<String, Any>()


                if (name.isNotEmpty()) {
                    updatesSecond["postProfileUser"] = name
                }


                if (photo.isNotEmpty() && selectedImage != null) {
                    updatesSecond["postProfileImage"] = photo
                }

                Firebase.firestore.collection(Approved_Post)
                    .whereEqualTo("uid", userId)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            Firebase.firestore.collection(Approved_Post)
                                .document(document.id)
                                .update(updatesSecond)
                                .addOnSuccessListener {
                                    // Update successful
                                }
                                .addOnFailureListener { e ->
                                    // Handle update failure
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        // Handle get documents failure
                    }

                val updates = hashMapOf<String, Any>()

                if (name.isNotEmpty()) {
                    updates["name"] = name
                }


                if (username.isNotEmpty()) {
                    updates["username"] = username
                }

                if (photo.isNotEmpty() && selectedImage != null) {
                    updates["photo"] = photo
                }

                if (bio.isNotEmpty()) {
                    updates["bio"] = bio
                }

                Firebase.firestore.collection(User_Node).document(userId)
                    .update(updates as Map<String, Any>)
                    .await()


            }
            true
        } catch (e: Exception) {
            false
        }

    }


    suspend fun addCommentForPost(postId: String, commentText: String): Boolean {


        return try {

            val snapshot = Firebase.firestore.collection(User_Node)
                .document(FirebaseAuth.getInstance().currentUser!!.uid).get().await()
            val tempList = snapshot.toObject<UserModel>()

            val postProfileUser = tempList?.name
            val postProfileImage = tempList?.photo


            val newComment = hashMapOf(
                "uid" to FirebaseAuth.getInstance().currentUser!!.uid,
                "postId" to postId,
                "commentText" to commentText,
                "userName" to postProfileUser,
                "userProfile" to postProfileImage // Replace with the actual username or user ID
            )
            Firebase.firestore.collection(Approved_Post).document(postId)
                .collection(User_Post_Comment).add(newComment)
                .addOnSuccessListener { documentReference ->
                    // Comment added successfully
                }
                .addOnFailureListener { e ->
                    // Handle any errors
                }
            true
        } catch (e: Exception) {
            false
        }


    }


    suspend fun deleteUserPost(item: PostModel): Boolean {
        return try {
            val userPostQuerySnapshot =
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Post_user)
                    .whereEqualTo("postCaption", item.postCaption)
                    .get().await()

            if (!userPostQuerySnapshot.isEmpty) {
                val userPostDocumentId = userPostQuerySnapshot.documents[0].id
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Post_user)
                    .document(userPostDocumentId)
                    .delete().await()

                val approvedPostQuerySnapshot = Firebase.firestore.collection(Approved_Post)
                    .whereEqualTo("postCaption", item.postCaption)
                    .get().await()

                if (!approvedPostQuerySnapshot.isEmpty) {
                    val approvedPostDocumentId = approvedPostQuerySnapshot.documents[0].id
                    Firebase.firestore.collection(Approved_Post)
                        .document(approvedPostDocumentId)
                        .delete().await()

                }
            } else {
                // Handle case where userPostQuerySnapshot is empty
            }
            true
        } catch (e: Exception) {
            false
        }
    }


    suspend fun deleteUserBookmark(item: PostModel) : Boolean {
        return try {


            val userPostQuerySnapshot =
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite)
                    .whereEqualTo("postCaption", item.postCaption)
                    .get().await()

            if (!userPostQuerySnapshot.isEmpty) {
                val userPostDocumentId = userPostQuerySnapshot.documents[0].id
                Firebase.firestore.collection(FirebaseAuth.getInstance().currentUser!!.uid + Favourite)
                    .document(userPostDocumentId)
                    .delete().await()
            } else {
                // Handle case where userPostQuerySnapshot is empty
            }

            true
        } catch (e: Exception) {
            false
        }


    }


}