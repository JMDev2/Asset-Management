package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.assets.adapter.ViewpagerAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.example.assets.R
import com.example.assets.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class HomeFragment : BaseDaggerFragment() {
    private lateinit var binding : FragmentHomeBinding

    val shipList: List<Assets> = ArrayList()


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[AssetViewModel::class.java]
    }

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
        binding.moreAssetsCard.setOnClickListener {
            findNavController().navigate(R.id.allAssetsFragment)
        }




        //search
       binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredShips = shipList.filter {
                    it.serial_number?.contains(newText.orEmpty(), ignoreCase = true) == true
                }
               // viewModel.filteredShips.postValue(filteredShips)
                return true
            }
        })
    }


}