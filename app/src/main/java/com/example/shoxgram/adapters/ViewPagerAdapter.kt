package com.example.shoxgram.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shoxgram.fragments.GroupFragment
import com.example.shoxgram.fragments.TabFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,var token:String) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                TabFragment.newInstance(token)
            }
            1->{
                GroupFragment.newInstance(token)
            }
            else->{
                Fragment()
            }
        }
    }
}