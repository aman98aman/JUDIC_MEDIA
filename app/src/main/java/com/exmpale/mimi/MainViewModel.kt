package com.exmpale.mimi

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.exmpale.mimi.Models.CommentModel
import com.exmpale.mimi.Models.NotificationModel
import com.exmpale.mimi.Models.PostModel
import com.exmpale.mimi.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    val auth = FirebaseAuth.getInstance()

    private val _registerSuccess = MutableLiveData<Boolean>()
    val registerSuccess: LiveData<Boolean> = _registerSuccess

    private val _userInfo = MutableLiveData<UserModel>()
    val userInfo: LiveData<UserModel> = _userInfo

    private val _postUploadResult = MutableLiveData<Boolean>()
    val postUploadResult: LiveData<Boolean> = _postUploadResult

    private val _uploadResultReals = MutableLiveData<Boolean>()
    val uploadResultReals: LiveData<Boolean> = _uploadResultReals

    private val _postLikedComment = MutableLiveData<Boolean>()
    val postLikedComment: LiveData<Boolean> = _postLikedComment

    private val _userPostList = MutableLiveData<List<PostModel>>()
    val userPostList: LiveData<List<PostModel>> = _userPostList

    private val _userPostTextList = MutableLiveData<List<PostModel>>()
    val userPostTextList: LiveData<List<PostModel>> = _userPostTextList

    private val _postList = MutableLiveData<List<PostModel>>()
    val postList: LiveData<List<PostModel>> = _postList

    private val _bookmarkedpostList = MutableLiveData<List<PostModel>>()
    val bookmarkedpostList: LiveData<List<PostModel>> = _bookmarkedpostList

    private val _userselectedpostList = MutableLiveData<List<PostModel>>()
    val userselectedpostList: LiveData<List<PostModel>> = _userselectedpostList

    private val _followingList = MutableLiveData<List<UserModel>>()
    val followingList: LiveData<List<UserModel>> = _followingList

    private val _postPhoto = MutableLiveData<List<PostModel>>()
    val postPhoto: LiveData<List<PostModel>> = _postPhoto

    private val _userPostCommentInfo = MutableLiveData<List<CommentModel>>()
    val userPostCommentInfo: LiveData<List<CommentModel>> = _userPostCommentInfo

    private val _favouritePostList = MutableLiveData<List<PostModel>>()
    val favouritePostList: LiveData<List<PostModel>> get() = _favouritePostList

    private val _postSavedSuccess = MutableLiveData<Boolean>()
    val postSavedSuccess: LiveData<Boolean> = _postSavedSuccess

    private val _fetchLikedUserInfo = MutableLiveData<List<NotificationModel>>()
    val fetchLikedUserInfo: LiveData<List<NotificationModel>> = _fetchLikedUserInfo

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult

    private val _updateCommentResult = MutableLiveData<Boolean>()
    val updateCommentResult: LiveData<Boolean> = _updateCommentResult

    private val _deletePostList = MutableLiveData<Boolean>()
    val deletePostList: LiveData<Boolean> = _deletePostList

    private val _deletePostBookmarkList = MutableLiveData<Boolean>()
    val deletePostBookmarkList: LiveData<Boolean> = _deletePostBookmarkList


    val repo = UserRepository()


    fun registerUser(
        uid: String,
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
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            val valueRegister = repo.registerUser(
                uid,
                name,
                username,
                email,
                password,
                bio,
                Photo,
                status,
                statuscaption,
                posts,
                follower,
                following
            )
            withContext(Dispatchers.Main) {
                _registerSuccess.value = valueRegister
            }

        }

    }

//
//    fun deleteFavouritePost (postImage: String){
//
//        try {
//            viewModelScope.launch(Dispatchers.IO){
//
//                repo.deleteFavoritePost(postImage)
//                val updatedList = _favouritePostList.value?.toMutableList()
//                updatedList?.removeAll { it.postImage == postImage }
//                _favouritePostList.postValue(updatedList!!)
//            }
//        }catch (_:Exception){
//
//        }

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchPosts()
                _postList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchBookmarkedPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchBookmarkedPosts()
                _bookmarkedpostList.postValue(tempList.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }


    fun fetchSelectedUserPosts(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {


                val tempList = repo.fetchSelectedUserPosts(uid)
                _userselectedpostList.postValue(tempList)


            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchPostsPhoto() {
        viewModelScope.launch(Dispatchers.IO) {
            try {


                val tempList = repo.fetchPostsPhoto()
                _postPhoto.postValue(tempList.reversed())


            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchUserData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchUserData(userId)
                _userInfo.postValue(tempList!!)

            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun saveFavouritePosts(item: PostModel) {
        viewModelScope.launch(Dispatchers.IO) {

            val tempList = repo.SaveUserPosts(item)

            withContext(Dispatchers.Main) {
                _postSavedSuccess.value = tempList

            }

        }
    }

    fun followingCount(updateCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateFollowing(updateCount)
        }
    }

    fun followerCount(uid: String, updateCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateFollower(uid, updateCount)
        }
    }


    fun fetchPostComment(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchPostComment(postId)
                _userPostCommentInfo.postValue(tempList)

            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun fetchLikedUserInformation(userUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchLikedUserInformation(userUid)
                _fetchLikedUserInfo.postValue(tempList.reversed())

            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }


    //    fun fetchUserPostsCountAndUploadPost(postImage: String, caption: String, callback: (Int?) -> Unit) {
    fun fetchUserPostsCountAndUploadPost(
        postImage: String,
        postCaption: String,
        postLike: Int,
        PostComment: Int,
        PostBookmark: Int,
        postType: Boolean,
        currentTime: String,
        param: (Any) -> Unit
    ) {
        viewModelScope.launch {
            repo.fetchUserPostsCount {
                viewModelScope.launch {
                    val result = repo.upLoadPostIntoFirebase(
                        postImage,
                        postCaption,
                        postLike,
                        PostComment,
                        PostBookmark,
                        postType,
                        currentTime
                    )
//                    callback(it?.plus(1)) // Return the updated post count

                    _postUploadResult.value = result


                }
            }
        }
    }

    fun publishLikedUserInformation(uid: String, message: String) {
        viewModelScope.launch {
            repo.fetchUserPostsCount {
                viewModelScope.launch {
                    val result = repo.publishLikedUserInformation(uid, message)

                    _postLikedComment.value = result


                }
            }
        }
    }


    fun fetchUserPostData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userPost = repo.fetchUserPostData(userId)
                _userPostList.postValue(userPost.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }


    fun fetchUserPostText(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userPost = repo.fetchUserPostText(userId)
                _userPostTextList.postValue(userPost.reversed())
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }


    fun deleteUserBookmark(item: PostModel) {

        try {
            viewModelScope.launch(Dispatchers.IO) {

                val result = repo.deleteUserBookmark(item)
                _deletePostBookmarkList.postValue(result)

            }
        } catch (_: Exception) {

        }
    }

    fun likeCount(updateCount: Int, documentId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateLike(updateCount, documentId)
        }
    }


    fun updateUserData(
        userName: String,
        email: String,
        image: String,
        bio: String,
        selectedImage: Uri
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            val updateResult = repo.updateUserData(userName, email, image, bio, selectedImage)

            withContext(Dispatchers.Main) {
                _updateResult.value = updateResult
            }

        }
    }


    fun uploadUserRealsStatus(realsVideo: String, realsCaption: String) {
        viewModelScope.launch(Dispatchers.IO) {

            viewModelScope.launch {
                val result = repo.upLoadRealsIntoFirebase(realsVideo, realsCaption)
                _uploadResultReals.value = result
            }
        }
    }


    fun addCommentForPost(postId: String, commentText: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val updateResult = repo.addCommentForPost(postId, commentText)

            withContext(Dispatchers.Main) {
                _updateCommentResult.value = updateResult
            }

        }
    }


    fun loginForUser(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
                //                    _loginSuccess.postValue(true)

//                } else {
//                    _loginSuccess.postValue(false)
//                }
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        _loginSuccess.postValue(true)

                    } else {
                        _loginSuccess.postValue(false)
                    }
                } else {
                    _loginSuccess.postValue(false)
                }
            }
    }


//    fun deleteFavouritePost (postCaption: String){
//
//        try {
//            viewModelScope.launch(Dispatchers.IO){
//
//                repo.deleteFavoritePost(postCaption)
//                val updatedList = _favouritePostList.value?.toMutableList()
//                updatedList?.removeAll { it.postCaption == postCaption }
//                _favouritePostList.postValue(updatedList!!)
//            }
//        }catch (_:Exception){
//
//        }
//    }

    fun fetchFollowingData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val tempList = repo.fetchFollowingData(userId)
                _followingList.postValue(tempList.reversed())


            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }


    fun deleteUserPost(item: PostModel) {

        try {
            viewModelScope.launch(Dispatchers.IO) {


                val result = repo.deleteUserPost(item)
                _deletePostList.postValue(result)

            }
        } catch (_: Exception) {

        }
    }


    fun uidText(context: Context, uidText: String) {

        val sharedPreferences =
            context.getSharedPreferences("MyUserId", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.putString("uid", uidText)
        editor.apply()


    }

}



