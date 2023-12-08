package com.ekenya.rnd.assets.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.animation.Easing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.toast
import com.example.assets.databinding.FragmentTotalAssetsBinding
import com.github.mikephil.charting.animation.Easing.EaseInOutCubic
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import javax.inject.Inject


class TotalAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentTotalAssetsBinding
    private lateinit var viewModel: AssetViewModel
    private val pieEntries: ArrayList<PieEntry> = ArrayList()

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTotalAssetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)[AssetViewModel::class.java]
        setupPieChart()
        totalAssets()

        observeAssetCountByDepartment("IT", binding.totalItAssets, Color.BLUE)
        observeAssetCountByDepartment("Hr", binding.totalHrAssets, Color.GREEN)
        observeAssetCountByDepartment("Marketing", binding.totalMarketingAssets, Color.RED)
    }

    private fun setupPieChart() {
        binding.chart.apply {
            // Set up general properties
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            // Enable and configure the hole
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            centerText = "Departments"

            // Enable rotation of the chart by touch
            isRotationEnabled = true
            rotationAngle = 0f

            // Highlight the values when tapped
            isHighlightPerTapEnabled = true

            // Set up legend
            val legend = legend
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.setDrawInside(false)
            legend.xEntrySpace = 7f
            legend.yEntrySpace = 0f
            legend.yOffset = 0f
        }
    }

    private fun totalAssets() {
        viewModel.assetsCount.observe(viewLifecycleOwner, Observer { count ->
            if (count > 0) {
                binding.totalAssets.text = "$count"
            } else {
                binding.totalAssets.text = "No Asset Saved"
            }
        })
        viewModel.retrieveAssetsCount()
    }

    private fun observeAssetCountByDepartment(department: String, textView: TextView, color: Int) {
        lifecycleScope.launch {
            viewModel.getAssetCountByDepartment(department).collect { count ->
                textView.text = "$count"

                // Update the data for the specific department
                val updatedEntries = ArrayList<PieEntry>(pieEntries)
                val index = updatedEntries.indexOfFirst { it.label == department }
                if (index != -1) {
                    updatedEntries[index] = PieEntry(count.toFloat(), department)
                } else {
                    updatedEntries.add(PieEntry(count.toFloat(), department))
                }

                // Set up dataset
                val dataSet = PieDataSet(updatedEntries, "Departments")
                dataSet.sliceSpace = 3f
                dataSet.selectionShift = 5f
                dataSet.colors = arrayListOf(Color.BLUE, Color.GREEN, Color.RED)

                // Set up data
                val data = PieData(dataSet)
                data.setValueTextSize(10f)
                data.setValueTextColor(Color.WHITE)

                // Apply data to the chart
                binding.chart.data = data
                binding.chart.invalidate()

                // Update the pieEntries list for future modifications
                pieEntries.clear()
                pieEntries.addAll(updatedEntries)
            }
        }
    }
}