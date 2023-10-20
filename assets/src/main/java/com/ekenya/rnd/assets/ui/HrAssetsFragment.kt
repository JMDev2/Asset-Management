package com.ekenya.rnd.assets.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekenya.rnd.assets.databinding.FragmentHrAssetsBinding
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment


class HrAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentHrAssetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHrAssetsBinding.inflate(inflater, container, false)
        return binding.root
    }


}