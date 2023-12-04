package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.common.utils.toast
import com.example.assets.databinding.FragmentAllAssetsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class AllAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAllAssetsBinding
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
        binding = FragmentAllAssetsBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAssets()
        setRecyclerView()
    }



    private fun setRecyclerView(){
        binding.recyclerview.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAssetsAdapter
        }
    }



    private fun observeAssets(){
        lifecycleScope.launch{
            viewModel.allAssets.collect(){ result ->
               when (result.status){
                   Status.SUCCESS -> {
                       binding.progressBar.visibility = View.GONE
                       val assets = result.data
                       assets?.let {
                           if (allAssetsAdapter == null){
                               allAssetsAdapter = AllAssetsAdapter(it)
                               setRecyclerView()
                           }else{
                               allAssetsAdapter?.updateData(it)
                           }
                       }
                   }
                   Status.LOADING -> {
                       binding.progressBar.visibility = View.VISIBLE
                   }
                   Status.ERROR -> {
                       binding.progressBar.visibility = View.VISIBLE
                       binding.errorText.visibility = View.VISIBLE
                   }
               }
            }
        }
    }




}