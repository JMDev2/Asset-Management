package com.ekenya.rnd.assets.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.graphics.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.assets.adapter.ViewpagerAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.example.assets.R
import com.example.assets.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment : BaseDaggerFragment() {
    private lateinit var binding : FragmentHomeBinding

    val shipList: List<Assets> = ArrayList()


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    var tabTitle = arrayOf("All", "IT", "Marketing", "HR", "Sales")

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

        observeProfile()

        binding.viewPager2.adapter = ViewpagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()


        binding.addCard.setOnClickListener {
            findNavController().navigate(R.id.addAssetsFragment)
        }
        binding.moreAssetsCard.setOnClickListener {
            findNavController().navigate(R.id.allCategoriesFragment)
        }
        binding.profileCard.setOnClickListener {
            val bottomSheetFragment = ProfileBottomSheetFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

        }


    }

    @SuppressLint("SetTextI18n")
    private fun observeProfile(){
        lifecycleScope.launch {
            viewModel._user.collect(){ profile ->
                when(profile.status){
                    Status.SUCCESS -> {
                        val _profile = profile.data
                        _profile?.let{
                            binding.profileName.text = _profile.name
                            binding.city.text = _profile.city+", "
                            binding.country.text = _profile.country
                            if (_profile.image != null) {
                                val bitmap = BitmapFactory.decodeByteArray(_profile.image, 0, _profile.image!!.size)
                                binding.image.setImageBitmap(bitmap)
                            }

                        }
                    }
                    Status.LOADING ->{

                }
                    Status.ERROR ->{

                }
                }
            }
        }
    }


}