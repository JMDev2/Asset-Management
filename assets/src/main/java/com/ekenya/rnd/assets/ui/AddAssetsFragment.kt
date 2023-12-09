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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ekenya.rnd.assets.database.AssetsDatabase
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.toast
import com.example.assets.R
import com.example.assets.databinding.FragmentAddAssetsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


class AddAssetsFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentAddAssetsBinding
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageByteArray: ByteArray? = null  // New field

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
        binding = FragmentAddAssetsBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        // Enable the back arrow in the toolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Set the click listener for the back arrow
        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveAssets()
        pickImage(view)
    }

    private fun pickImage(view: View) {
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

                binding.assetImgDisplay.setImageURI(imageUri)

                // Convert the selected image to a ByteArray
                selectedImageByteArray = imageUri?.let { convertImageToByteArray(it) }

                // Get the image file name
                val imageFileName = imageUri?.let { getFileName(it) }
                binding.chooseImgTxt.text = imageFileName


            }
        }
    }

    //Help[er function to pick the image name
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


    // Helper function to convert an image to a ByteArray
    private fun convertImageToByteArray(uri: Uri): ByteArray? {
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            return inputStream?.readBytes()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    // Modify the saveAssets method
    private fun saveAssets() {
        binding.button.setOnClickListener {
            val validationResult = validateInput()

            if (validationResult.isValid) {
                val assets = validationResult.assets

                // Set the image data to the assets object
                assets?.image = selectedImageByteArray

                if (assets != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (viewModel.isSerialNumberUnique(assets.serial_number.toString())) {
                            viewModel.saveAsset(assets)
                            Log.d("Maina", "Database operation performed (Save): $assets")
                            findNavController().navigate(R.id.homeFragment)
                        } else {
                            // Display a toast message when the serial number is not unique
                            toast("Serial number already exists")
                        }
                    }
                }
            }
        }
    }

    // Helper function to validate user input
    private fun validateInput(): ValidateResult {
        val name = binding.assetName.editText?.text.toString()
        val serialNumber = binding.assetSn.editText?.text.toString()
        val department = binding.assetDep.editText?.text.toString()
        val city = binding.assetCity.editText?.text.toString()
        val model = binding.assetModel.editText?.text.toString()
        val otherAttributes = binding.assetOtherAttr.editText?.text.toString()
        val description = binding.description.text.toString()

        if (name.isEmpty()) {
            binding.assetName.error = "Field must not be empty"
            return ValidateResult(false, null)
        }

        return ValidateResult(true, Assets(name = name, serial_number = serialNumber, department = department, city = city, model = model, other_attributes = otherAttributes, description = description, image = selectedImageByteArray))
    }

    // Data class to hold validation result
    private data class ValidateResult(val isValid: Boolean, val assets: Assets? = null)
}
