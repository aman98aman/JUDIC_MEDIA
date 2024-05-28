package com.exmpale.mimi.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.exmpale.mimi.R
import com.exmpale.mimi.databinding.ActivityViewStoryBinding

class ViewStoryActivity : AppCompatActivity() {

    val binding by lazy { ActivityViewStoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        setContentView(binding.root)

        val profileImageId = intent.getIntExtra("profileImageId", 0)
        val imageId = intent.getIntExtra("imageId", 0)
        val postTimeId = intent.getStringExtra("postTimeId")
        val userNameId = intent.getStringExtra("userNameId")

        binding.apply {

            postUserName.text = userNameId
            userProfileHolder.setImageResource(profileImageId)
            storyImageHolder.setImageResource(imageId)
            postTime.text = postTimeId

        }


        Handler(Looper.myLooper()!!).postDelayed({

           finish()

        }, 5000)


        binding.progressHorizontal.progress = 0

        val handler = Handler(Looper.myLooper()!!)
        val delayMillis = 50 // Adjust this value to change the speed of progress

        val maxProgress = 100 // Assuming 100 is the maximum progress value
        val totalTime = 5000 // 5000 milliseconds = 5 seconds
        val increment = maxProgress.toFloat() / (totalTime / delayMillis)

        val runnable = object : Runnable {
            var currentProgress = 0f

            override fun run() {
                currentProgress += increment
                binding.progressHorizontal.progress = currentProgress.toInt()

                if (currentProgress < maxProgress) {
                    handler.postDelayed(this, delayMillis.toLong())
                }
            }
        }

        handler.postDelayed(runnable, delayMillis.toLong())


    }
}