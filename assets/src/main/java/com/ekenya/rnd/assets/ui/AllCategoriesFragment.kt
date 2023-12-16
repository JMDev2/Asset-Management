package com.ekenya.rnd.assets.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.assets.adapter.AllAssetsAdapter
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.Status
import com.ekenya.rnd.common.utils.toast
import com.example.assets.R
import com.example.assets.databinding.FragmentAllCategoriesBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
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

        if (binding.recyclerview == null){
            binding.errorText.visibility = View.VISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    //tool bar menu item
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
                        onItemClick()
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
        allAssetsAdapter = AllAssetsAdapter(viewModel,theFilteredAssests)
        binding.recyclerview.adapter = allAssetsAdapter
        allAssetsAdapter!!.notifyDataSetChanged()
    }



    private fun setRecyclerView(){
        binding.recyclerview.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = allAssetsAdapter
        }
    }


    //Delete assets
    private fun swipeToDelete(){
        val assets = Assets()
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                allAssetsAdapter?.removeItem(position)
                binding.root.let {
                    showSnackBar(it, assets)
                }
            }

        }).attachToRecyclerView(binding.recyclerview)
    }


    private fun showSnackBar(view: View, deletedShip: Assets) {
        val snackbar = Snackbar.make(
            view,
            "Deleted",
            Snackbar.LENGTH_LONG
        )

        snackbar.setAction("Undo") {
            viewModel.saveAsset(deletedShip)
        }

        // Set a callback to perform an action after the Snackbar is dismissed
        snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event == DISMISS_EVENT_TIMEOUT) {
                    toast("Ship Deleted")
                }
            }
        })

        snackbar.show()
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
                                allAssetsAdapter = AllAssetsAdapter(viewModel,it)
                                setRecyclerView()
                                onItemClick()
                                swipeToDelete()
                            }
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        toast("Loading")
                        Log.e("Loading","Loading")
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                        toast("Error")
                        Log.e("Loading", "Error")
                    }
                }
            }
        }
    }


}