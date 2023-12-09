package com.ekenya.rnd.assets.ui

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.SharedPreferences.getSavedCountFromSharedPreferences
import com.ekenya.rnd.common.utils.Status
import com.example.assets.R
import com.example.assets.databinding.FragmentAssestDetailsBinding
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AssestDetailsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAssestDetailsBinding
    private lateinit var assets: Assets

    @Inject
    lateinit var profileFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, profileFactory)[ProfileViewModel::class.java]
    }


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


        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

            if (percentage == 1f) {
                binding.toolBar.title = assets.name
                binding.toolBar.visibility = View.VISIBLE
            } else {
                binding.toolBar.title = ""
                binding.toolBar.visibility = View.GONE
            }
        })

        // Enable the back arrow in the toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the click listener for the back arrow
        binding.toolBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        binding.addCard.setOnClickListener {
            findNavController().navigate(R.id.addAssetsFragment)
        }
        binding.moreAssetsCard.setOnClickListener {
            findNavController().navigate(R.id.allCategoriesFragment)
        }
        binding.profileCard.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        observeDetails()
        observeProfile()
       //totalAssets()

    }

    @SuppressLint("SuspiciousIndentation")
    private fun observeDetails() {
        assets = requireArguments().getParcelable<Assets>("assetItem")!!
        binding.assetName.text = assets.name
        binding.assetSerialNumber.text = assets.serial_number
        binding.assetDepartment.text = assets.department
        binding.assetCity.text = assets.city
        binding.assetModel.text = assets.model
        binding.assetOtherAttributes.text = assets.other_attributes
        binding.assetDescription.text = assets.description


        if (assets.image != null) {
            val bitmap = BitmapFactory.decodeByteArray(assets.image, 0, assets.image!!.size)
            binding.image.setImageBitmap(bitmap)
        }
        val savedCount = getSavedCountFromSharedPreferences(requireContext())
        binding.total.text = savedCount.toString()
    }




    private fun observeProfile() {
        lifecycleScope.launch {
            viewModel._user.collect() { profile ->
                when (profile.status) {
                    Status.SUCCESS -> {
                        val _profile = profile.data
                        _profile?.let {
                            binding.userName.text = _profile.name
                            binding.userStatus.text = _profile.status
                            binding.userCountry.text = _profile.country

                            val currentDate = Date()
                            val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
                            val formattedDate = dateFormat.format(currentDate)

                            binding.date.text = formattedDate


                        }
                    }
                    Status.LOADING -> {

                    }
                    Status.ERROR -> {

                    }
                }
            }
        }
    }


}