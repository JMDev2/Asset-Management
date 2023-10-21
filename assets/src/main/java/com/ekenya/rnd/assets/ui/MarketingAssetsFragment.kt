package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.example.assets.databinding.FragmentMarketingAssetsBinding


class MarketingAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentMarketingAssetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMarketingAssetsBinding.inflate(inflater, container, false)
        return binding.root
    }


}