package com.exmpale.mimi.Utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exmpale.mimi.Fragments.ExploreFragment
import com.exmpale.mimi.Fragments.HomeFragment
import com.exmpale.mimi.Fragments.NotificationFragment
import com.exmpale.mimi.Fragments.UserFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> HomeFragment()
            1 -> ExploreFragment()
            2 -> NotificationFragment()
            3 -> UserFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }

    }

}
