package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.toast
import com.example.assets.databinding.FragmentTotalAssetsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class TotalAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentTotalAssetsBinding


    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, factory)[AssetViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTotalAssetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalAssets()

        observeAssetCountByDepartment("IT", binding.totalItAssets)
        observeAssetCountByDepartment("Hr", binding.totalHrAssets)
        observeAssetCountByDepartment("Marketing", binding.totalMarketingAssets)


    }

    private fun totalAssets(){
        viewModel.assetsCount.observe(viewLifecycleOwner, androidx.lifecycle.Observer{ count ->
            toast("Total Assets: $count")
            Log.e("Total","Total: $count")
            if (count > 0) {
                binding.totalAssets.text = "$count"
            }else{
                binding.totalAssets.text = "No Asset Saved"
            }
        })
        viewModel.retrieveAssetsCount()


    }

    private fun observeAssetCountByDepartment(department: String, textView: TextView) {
        lifecycleScope.launch {
            viewModel.getAssetCountByDepartment(department).collect { count ->
                // Update the specific TextView with the total count for the department
                textView.text = "$count"

            }
        }
    }



}