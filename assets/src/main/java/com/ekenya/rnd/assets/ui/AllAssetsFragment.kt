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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.common.utils.toast
import com.example.assets.R
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
        if (binding.recyclerview == null){
            binding.textView.visibility = View.VISIBLE
        }else{
            setRecyclerView()
        }

    }

    private fun onItemClick(){
        allAssetsAdapter?.onItemClick = { asset ->
            val bundle = Bundle()
            bundle.putParcelable("assetItem", asset)
            requireView().findNavController().navigate(
                R.id.assestDetailsFragment,
                bundle
            )
            binding.recyclerview.adapter = allAssetsAdapter

        }
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
                           if (allAssetsAdapter == null) {
                               allAssetsAdapter = AllAssetsAdapter(viewModel, it)
                               setRecyclerView()
                               onItemClick()
                           }

                       }
                   }
                   Status.LOADING -> {
                       binding.progressBar.visibility = View.VISIBLE
                       binding.textView.visibility = View.GONE
                   }
                   Status.ERROR -> {
                       binding.progressBar.visibility = View.VISIBLE
                       binding.textView.visibility = View.VISIBLE
                   }
               }
            }
        }
    }
}