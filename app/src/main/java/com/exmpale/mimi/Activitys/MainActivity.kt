package com.exmpale.mimi.Activitys

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.exmpale.mimi.R
import com.exmpale.mimi.Utilities.ViewPagerAdapter
import com.exmpale.mimi.databinding.ActivityMainBinding
import com.exmpale.mimi.utils.Tokon_Center
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.darkGreen)
        setContentView(binding.root)

       getButton()
        val floatingColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        val colorStateList1 = ColorStateList.valueOf(floatingColor)
        binding.floatingActionButton.imageTintList = colorStateList1

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, CreatePostActivity::class.java))
        }

    }


    private fun getButton() {

        val color1 = ContextCompat.getColor(this@MainActivity, R.color.blue)
        val color2 = ContextCompat.getColor(this@MainActivity, R.color.white)
        val colorStateList = ColorStateList.valueOf(color1)
        binding.homeBtn.imageTintList = colorStateList

        binding.homeBtn.setOnClickListener { binding.viewPager.currentItem = 0

            val colorStateList1 = ColorStateList.valueOf(color2)
            val colorStateList2 = ColorStateList.valueOf(color1)
            binding.homeBtn.imageTintList = colorStateList2
            binding.exploreBtn.imageTintList = colorStateList1
            binding.notifyBtn.imageTintList = colorStateList1
            binding.userBtn.imageTintList = colorStateList1


        }

        binding.exploreBtn.setOnClickListener { binding.viewPager.currentItem = 1

            val colorStateList1 = ColorStateList.valueOf(color1)
            val colorStateList2 = ColorStateList.valueOf(color2)
            binding.exploreBtn.imageTintList = colorStateList1
            binding.homeBtn.imageTintList = colorStateList2
            binding.notifyBtn.imageTintList = colorStateList2
            binding.userBtn.imageTintList = colorStateList2

        }

        binding.notifyBtn.setOnClickListener { binding.viewPager.currentItem = 2

            val colorStateList1 = ColorStateList.valueOf(color1)
            val colorStateList2 = ColorStateList.valueOf(color2)

            binding.exploreBtn.imageTintList = colorStateList2
            binding.homeBtn.imageTintList = colorStateList2
            binding.notifyBtn.imageTintList = colorStateList1
            binding.userBtn.imageTintList = colorStateList2

        }

        binding.userBtn.setOnClickListener { binding.viewPager.currentItem = 3

            val colorStateList1 = ColorStateList.valueOf(color1)
            val colorStateList2 = ColorStateList.valueOf(color2)

            binding.exploreBtn.imageTintList = colorStateList2
            binding.homeBtn.imageTintList = colorStateList2
            binding.notifyBtn.imageTintList = colorStateList2
            binding.userBtn.imageTintList = colorStateList1

        }

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                when (position) {

                    0 -> { val colorStateList1 = ColorStateList.valueOf(color2)
                        val colorStateList2 = ColorStateList.valueOf(color1)
                        binding.homeBtn.imageTintList = colorStateList2
                        binding.exploreBtn.imageTintList = colorStateList1
                        binding.notifyBtn.imageTintList = colorStateList1
                        binding.userBtn.imageTintList = colorStateList1
                    }

                    1 -> {val colorStateList1 = ColorStateList.valueOf(color1)
                        val colorStateList2 = ColorStateList.valueOf(color2)
                        binding.exploreBtn.imageTintList = colorStateList1
                        binding.homeBtn.imageTintList = colorStateList2
                        binding.notifyBtn.imageTintList = colorStateList2
                        binding.userBtn.imageTintList = colorStateList2}

                    3 -> {


                        val colorStateList1 = ColorStateList.valueOf(color1)
                        val colorStateList2 = ColorStateList.valueOf(color2)

                        binding.exploreBtn.imageTintList = colorStateList2
                        binding.homeBtn.imageTintList = colorStateList2
                        binding.notifyBtn.imageTintList = colorStateList2
                        binding.userBtn.imageTintList = colorStateList1


                    }

                    else -> {

                        val colorStateList1 = ColorStateList.valueOf(color1)
                        val colorStateList2 = ColorStateList.valueOf(color2)

                        binding.exploreBtn.imageTintList = colorStateList2
                        binding.homeBtn.imageTintList = colorStateList2
                        binding.notifyBtn.imageTintList = colorStateList1
                        binding.userBtn.imageTintList = colorStateList2


                    }
                }

            }
        })
    }
}