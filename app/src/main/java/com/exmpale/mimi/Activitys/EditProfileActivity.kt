package com.exmpale.mimi.Activitys

import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.Models.UserModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityEditProfileBinding
import com.exmpale.mimi.utils.User_Profile_Folder
import com.exmpale.mimi.utils.uploadImage
import com.google.firebase.auth.FirebaseAuth

class EditProfileActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    var profileImage: String? = null
    var selectedImageUri: Uri? = null
    lateinit var user: UserModel
    private lateinit var viewModel: MainViewModel
    lateinit var progressDialog: ProgressDialog


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uriImage ->


            uriImage.let {
                selectedImageUri = it

                uploadImage(uriImage!!, User_Profile_Folder, progressDialog) {
                    if (it == null) {

                    } else {

                        profileImage = it
                        binding.profileImage.setImageURI(uriImage)

                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        user = UserModel()

        setUserData()

        binding.saveImage.setOnClickListener {

            if (binding.profileImage.drawable == null) {
                Toast.makeText(this, "Choose image", Toast.LENGTH_LONG).show()

            } else {
                val newName = binding.nameInput.text.toString()
                val newUserName = binding.usernameInput.text.toString()
                val newBio = binding.bioInput.text.toString()
                val newImage = "$profileImage"
                if (selectedImageUri != null) {
                    viewModel.updateUserData(
                        newName,
                        newUserName,
                        newImage,
                        newBio,
                        selectedImageUri!!
                    )
                    finish()

                } else {
                    Toast.makeText(this, "Choose image", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.backImage.setOnClickListener {
            showAlertDialog()
        }

        binding.changePhotoBt.setOnClickListener {
            startForProfileImageResult.launch("image/*")
        }

        viewModel.updateResult.observe(this, Observer { isSuccess ->

            if (isSuccess) {
                Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_LONG).show()
            }

        })

    }

    private fun setUserData() {

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        viewModel.fetchUserData(userId)

        viewModel.userInfo.observe(this, Observer { user ->
            binding.nameInput.setText(user.name)
            binding.usernameInput.setText(user.username)
            binding.bioInput.setText(user.bio)
            Glide.with(this).load(user.photo).into(binding.profileImage)
        })
    }

    private fun showAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage("Are you sure about that ?")
            .setPositiveButton("Yes") { dialogInterface, which ->
                finish()
            }
            .setNeutralButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}