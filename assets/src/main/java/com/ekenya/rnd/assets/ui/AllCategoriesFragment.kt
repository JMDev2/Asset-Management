package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.common.utils.toast
import com.example.assets.R
import com.example.assets.databinding.FragmentAllCategoriesBinding
import kotlinx.coroutines.launch
import javax.inject.Inject


class AllCategoriesFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAllCategoriesBinding

    private var allAssetsAdapter: AllAssetsAdapter? = null

    var filteredAssets: List<Assets> = ArrayList()


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
        binding = FragmentAllCategoriesBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Enable the back arrow in the toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set the click listener for the back arrow
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAssets()
        setRecyclerView()




        //perfoming search


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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                // Handle the search option
                val searchItem = item.actionView as SearchView
                searchItem.isIconified = false // Expand the SearchView

                // Set a query text listener for the SearchView
                searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // Handle the submission of the search query (if needed)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Perform filtering or live search as the user types
                        filterShip(newText.orEmpty())
                        return true
                    }
                })
                return true
            }
            R.id.add_new -> {
                // Handle the add new option
                findNavController().navigate(R.id.addAssetsFragment)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun filterShip(assets: String){
        val filteredList = filteredAssets.filter { it.name?.contains(assets, ignoreCase = true) ?: false }
        val theFilteredAssests = ArrayList(filteredList)

        if (theFilteredAssests.isEmpty()){
            binding.errorText.visibility = View.VISIBLE
        }else{
            binding.errorText.visibility = View.GONE
        }
        allAssetsAdapter = AllAssetsAdapter(theFilteredAssests)
        binding.recyclerview.adapter = allAssetsAdapter
        allAssetsAdapter!!.notifyDataSetChanged()
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
                                filteredAssets = assets
                                allAssetsAdapter = AllAssetsAdapter(it)
                                setRecyclerView()
                                onItemClick()
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                    }
                }
            }
        }
    }


}