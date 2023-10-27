package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.example.assets.databinding.FragmentHrAssetsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class HrAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentHrAssetsBinding

    private var allAssetsAdapter: AllAssetsAdapter? = null


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, factory)[AssetViewModel::class.java]
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeHrAssets()

    }

    private fun setRecyclerView(){
        binding.hrRecyclerview.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAssetsAdapter
        }
    }


    private fun observeHrAssets(){
        lifecycleScope.launch{
            viewModel.getHrAssets().collect(){ result ->
                when (result.status){
                    Status.SUCCESS -> {
                        binding.hrProgressBar.visibility = View.GONE
                        val assets = result.data
                        assets?.let {
                            if (allAssetsAdapter == null){
                                allAssetsAdapter = AllAssetsAdapter(it)
                                setRecyclerView()
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.hrProgressBar.visibility = View.VISIBLE

                    }
                    Status.ERROR -> {
                        binding.hrProgressBar.visibility = View.GONE

                    }
                }
            }
        }
    }





}