package com.example.tablyout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerMessengerAdapter(private val fm:FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        //return number of fragments or tabs because view pager will slide on these
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChatFragment()
            }

            1 -> {
                StatusFragment()
            }

            else -> {
                CallsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->{
                "Chats"
            }

            1->{
                "Status"
            }

            else->{
                "Calls"
            }
        }
    }
}