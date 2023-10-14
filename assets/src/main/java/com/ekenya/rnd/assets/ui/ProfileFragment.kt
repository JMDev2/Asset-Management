package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekenya.rnd.assets.R
import com.ekenya.rnd.assets.databinding.FragmentHomeBinding
import com.ekenya.rnd.assets.databinding.FragmentProfileBinding
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment

class ProfileFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentProfileBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }


}