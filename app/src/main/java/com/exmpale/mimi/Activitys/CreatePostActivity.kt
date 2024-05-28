package com.exmpale.mimi.Activitys

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.databinding.ActivityCreatePostBinding
import com.exmpale.mimi.utils.Post_Image
import com.exmpale.mimi.utils.Post_Video
import com.exmpale.mimi.utils.uploadImage
import com.exmpale.mimi.utils.uploadVideo
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePostActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreatePostBinding.inflate(layoutInflater) }
    var postImage: String? = null
    var realsVideo: String? = null
    lateinit var progressDialog: ProgressDialog
    private lateinit var viewModel: MainViewModel



    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->


            uriImage.let {

                if(it == null){
                    Toast.makeText(this, "Image not selected try again", Toast.LENGTH_LONG).show()
                }else{
                    uploadImage(uriImage!!, Post_Image, progressDialog) {
                        if (it == null) {

                        } else {
                            Toast.makeText(this, "Image upload done now write Caption", Toast.LENGTH_LONG).show()

                            postImage = it
//                            binding.postImage.setImageURI(uriImage)
                            Glide
                                .with(this)
                                .load(postImage)
                                .into(binding.checkImage)


                        }
                    }
                }
            }
        }


    private val startForRellsResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriVideo ->


            uriVideo.let {

                uploadVideo(uriVideo!!, Post_Video, progressDialog) {
                    if (it == null) {

                    } else {
                        Toast.makeText(this, "Status upload done now write Caption", Toast.LENGTH_LONG).show()

                        realsVideo = it

                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding.uImage.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }

        binding.backIcon.setOnClickListener {
            finish()
        }

        binding.uVideo.setOnClickListener {
            startForRellsResult.launch("video/*")
        }

        binding.button2.setOnClickListener {
            setPostToFirebase()
        }

        viewModel.postUploadResult.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration successful
                Toast.makeText(this, "Post Upload Successful for public view please wait for approval", Toast.LENGTH_LONG).show()
                finish()
            } else {
                // Registration failed
                Toast.makeText(this, "Post Upload Failed", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.uploadResultReals.observe(this) { isSuccess ->
            if (isSuccess) {
                // Registration successful
                Toast.makeText(this, "Reals Upload Successful", Toast.LENGTH_LONG).show()
                finish()
            } else {
                // Registration failed
                Toast.makeText(this, "Reals Upload Failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setPostToFirebase(){


        var text = binding.postText.text.toString()
        val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")

        val currentDateAndTime = sdf.format(Date())

        if (text.isNotEmpty()){
            if (postImage != null) {

                viewModel.fetchUserPostsCountAndUploadPost(postImage!!, text, 0, 0 , 0, false, currentDateAndTime){ updateCount->
                 finish()
                }
            }else if(realsVideo != null){
                viewModel.uploadUserRealsStatus(realsVideo!!, text)

            }else{
                viewModel.fetchUserPostsCountAndUploadPost("", text, 0, 0 , 0,true, currentDateAndTime) { updateCount ->

                    finish()
                }

            }
        }else{

            Toast.makeText(this, "Caption is empty", Toast.LENGTH_LONG).show()

        }

    }


}