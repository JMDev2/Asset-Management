package com.ekenya.rnd.assets.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.example.assets.R
import com.example.assets.databinding.FragmentAddAssetsBinding
import javax.inject.Inject


class AddAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAddAssetsBinding


    private val PICK_IMAGE_REQUEST = 1
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
        binding = FragmentAddAssetsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveAssets()
        pickImage(view)

    }


    private fun pickImage(view: View){
        binding.imageLayout.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val imageUri = data.data


                // Get the image file name
                val imageFileName = imageUri?.let { getFileName(it) }
                binding.chooseImgTxt.text = imageFileName

            }
        }
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {
        val contentResolver = requireContext().contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        return try {
            if (cursor != null && cursor.moveToFirst()) {
                val displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                displayName
            } else {
                null
            }
        } finally {
            cursor?.close()
        }
    }





    private fun saveAssets(){
        binding.button.setOnClickListener {
            val validationResult = validateInput()

            if (validationResult.isValid){
                val assets = validationResult.assets
                if (assets != null){
                    viewModel.saveAsset(assets)
                    Log.d("Maina", "Database operation performed (Save): $assets")
                    findNavController().navigate(R.id.homeFragment)
                }
            }
        }
    }

    private fun validateInput(): ValidateResult{
        val name = binding.assetName.editText?.text.toString()
        val serialNumber = binding.assetSn.editText?.text.toString()
        val departemnt = binding.assetDep.editText?.text.toString()
        val city = binding.assetCity.editText?.text.toString()
        val model = binding.assetModel.editText?.text.toString()
        val other_att = binding.assetOtherAttr.editText?.text.toString()
        val description = binding.description.text.toString()


        if (name.isEmpty()){
            binding.assetName.error = "Field must not be empty"
        }

        Log.e("maina","name: $name")

        return ValidateResult(true, Assets(name = name, serial_number = serialNumber, department = departemnt, city = city, model = model, other_attributes = other_att, description = description))

    }

    private data class ValidateResult(val isValid: Boolean, val assets: Assets? = null)


}