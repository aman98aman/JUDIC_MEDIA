package com.exmpale.mimi.Utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exmpale.mimi.Fragments.LiveFragment
import com.exmpale.mimi.Fragments.PostFragment
import com.exmpale.mimi.Fragments.StoryFragment


class GalleryViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PostFragment()
            1 -> StoryFragment()
            2 -> LiveFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}