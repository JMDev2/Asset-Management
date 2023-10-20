package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.assets.R
import com.ekenya.rnd.assets.adapter.ViewpagerAdapter
import com.ekenya.rnd.assets.databinding.FragmentHomeBinding
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseDaggerFragment() {
    private lateinit var binding : FragmentHomeBinding

    var tabTitle = arrayOf("All", "IT", "Marketing", "HR")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.adapter = ViewpagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()


        binding.addCard.setOnClickListener {
            findNavController().navigate(R.id.addAssetsFragment)
        }
    }


}