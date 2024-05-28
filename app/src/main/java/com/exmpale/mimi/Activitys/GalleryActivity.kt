package com.exmpale.mimi.Activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.exmpale.mimi.Utilities.GalleryViewPagerAdapter
import com.exmpale.mimi.databinding.ActivityGalleryBinding
import com.google.android.material.tabs.TabLayoutMediator

class GalleryActivity : AppCompatActivity() {

    val binding by lazy { ActivityGalleryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.apply {
            val adapter = GalleryViewPagerAdapter(this@GalleryActivity)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {

                    0 -> tab.text = "Post"
                    1 -> tab.text = "Story"
                    2 -> tab.text = "Live"

                }
            }.attach()

        }



    }

}