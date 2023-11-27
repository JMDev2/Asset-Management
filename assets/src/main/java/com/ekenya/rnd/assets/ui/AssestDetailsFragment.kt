package com.ekenya.rnd.assets.ui

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.example.assets.R
import com.example.assets.databinding.FragmentAssestDetailsBinding


class AssestDetailsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAssestDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAssestDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have a Toolbar in your layout with ID "toolbar"
        binding.toolBar.title = "Your Title"

        // If you want to enable the back arrow
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the click listener for the back arrow
        binding.toolBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }


    }


}