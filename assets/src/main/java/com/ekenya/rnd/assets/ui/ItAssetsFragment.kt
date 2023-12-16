package com.ekenya.rnd.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.assets.ui.AssetViewModel
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.example.assets.R
import com.example.assets.databinding.FragmentItAssetsBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class ITAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentItAssetsBinding
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
        binding = FragmentItAssetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeITAssets()
        setRecyclerView()


    }

    private fun onItemClick(){
        allAssetsAdapter?.onItemClick = { asset ->
            val bundle = Bundle()
            bundle.putParcelable("assetItem", asset)
            requireView().findNavController().navigate(
                R.id.assestDetailsFragment,
                bundle
            )
            binding.itRecyclerview.adapter = allAssetsAdapter

        }
    }

    private fun setRecyclerView(){
        binding.itRecyclerview.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAssetsAdapter
        }
    }


    private fun observeITAssets(){
        lifecycleScope.launch{
            viewModel.getITAssets().collect(){ result ->
                when (result.status){
                    Status.SUCCESS -> {
                        binding.itProgressBar.visibility = View.GONE
                        val assets = result.data
                        assets?.let {
                            if (allAssetsAdapter == null){
                                allAssetsAdapter = AllAssetsAdapter(viewModel,it)
                                setRecyclerView()
                                onItemClick()
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.itProgressBar.visibility = View.VISIBLE
                        binding.textView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        binding.itProgressBar.visibility = View.GONE
                        binding.textView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


}