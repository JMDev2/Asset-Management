package com.ekenya.rnd.assets.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ekenya.rnd.assets.ITAssetsFragment
import com.ekenya.rnd.assets.ui.AllAssetsFragment
import com.ekenya.rnd.assets.ui.HrAssetsFragment
import com.ekenya.rnd.assets.ui.MarketingAssetsFragment

class ViewpagerAdapter(fragmentManager : FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return AllAssetsFragment()
            1 -> return ITAssetsFragment()
            2 -> return MarketingAssetsFragment()
            3 -> return HrAssetsFragment()
            4 -> return ITAssetsFragment()
            else -> return AllAssetsFragment()
        }
    }
}