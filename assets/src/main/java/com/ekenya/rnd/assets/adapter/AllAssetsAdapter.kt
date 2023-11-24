package com.ekenya.rnd.assets.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.rnd.baseapp.model.Assets
import com.example.assets.databinding.ItemLayoutBinding

class AllAssetsAdapter(var assets: List<Assets>):
    RecyclerView.Adapter<AllAssetsAdapter.AssetsViewHolder>() {
        inner class AssetsViewHolder(val binding: ItemLayoutBinding,val context: Context) : RecyclerView.ViewHolder(binding.root){
            fun bind(assets: Assets){
                binding.assetName.text = assets.name
                binding.assetSn.text = assets.serial_number
                binding.assetDep.text = assets.department

                if (assets.image != null) {
                    val bitmap = BitmapFactory.decodeByteArray(assets.image, 0, assets.image!!.size)
                    binding.assetImg.setImageBitmap(bitmap)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetsViewHolder {
        return AssetsViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false), parent.context
        )
    }


    override fun onBindViewHolder(holder: AssetsViewHolder, position: Int) {
        holder.bind(assets[position])

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = assets.size

    fun updateData(newData: List<Assets>) {
        assets = newData
        notifyDataSetChanged()
    }
}