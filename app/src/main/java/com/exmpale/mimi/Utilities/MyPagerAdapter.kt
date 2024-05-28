package com.exmpale.mimi.Utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exmpale.mimi.Fragments.AllPhotoFragment
import com.exmpale.mimi.Fragments.AllTextFragment
import com.exmpale.mimi.Fragments.BookmarkFragment

class MyPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllPhotoFragment()
            1 -> AllTextFragment()
            2 -> BookmarkFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}