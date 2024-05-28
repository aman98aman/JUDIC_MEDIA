package com.exmpale.mimi.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.exmpale.mimi.MainViewModel
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityVideoBinding
import com.google.firebase.auth.FirebaseAuth

class VideoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityVideoBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        binding.backIcon.setOnClickListener {
            finish()
        }


        val editor = getSharedPreferences("MyUserId", MODE_PRIVATE)



        val userId = editor.getString("uid", null)
        viewModel.fetchUserData(userId!!)

        viewModel.userInfo.observe(this, Observer { user ->


            binding.videoViewRell.setVideoPath(user.status)
            binding.rellCaption.text = user.statuscaption
            Glide.with(this).load(user.photo).into(binding.rellProfileImage)



        })

        binding.videoViewRell.setOnPreparedListener {
            binding.progressBar.visibility = View.GONE
            binding.videoViewRell.start()
        }
    }
}