package com.ekenya.rnd.assets.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekenya.rnd.assets.databinding.FragmentAllAssetsBinding
import com.ekenya.rnd.assets.databinding.FragmentMarketingAssetsBinding
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment


class AllAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAllAssetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAssetsBinding.inflate(inflater, container, false)
        return binding.root
    }


}