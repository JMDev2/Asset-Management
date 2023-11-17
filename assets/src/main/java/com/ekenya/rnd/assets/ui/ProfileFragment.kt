package com.ekenya.rnd.assets.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ekenya.rnd.baseapp.model.Profile
import com.ekenya.rnd.common.abstractions.BaseDaggerFragment
import com.ekenya.rnd.common.utils.toast
import com.example.assets.R
import com.example.assets.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ProfileFragment : BaseDaggerFragment() {
    private lateinit var binding: FragmentProfileBinding

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageByteArray: ByteArray? = null  // New field


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveUser()
        pickImage(view)



    }

    private fun pickImage(view: View) {
        binding.imageCard.setOnClickListener {
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

                binding.imageView.setImageURI(imageUri)

                // Convert the selected image to a ByteArray
                selectedImageByteArray = imageUri?.let { convertImageToByteArray(it) }



            }
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

    private fun saveUser() {
        binding.btn.setOnClickListener {
            val validationResult = validateInput()

            if (validationResult.isValid) {
                val profile = validationResult.profile

                // Set the image data to the assets object
                profile?.image = selectedImageByteArray

                if (profile != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val userExists = viewModel.checkUserExists(profile.email)
                        if (!userExists) {
                            viewModel.saveUser(profile)
                            Log.d("maina", "Database operation performed (Save): $profile")
                            Log.d("maina", "Database operation performed (id): ${profile.email}")
                            // findNavController().navigate(R.id.homeFragment)
                            // Display a toast message when the serial number is not unique
                            toast("Saved")
                        } else {
                            toast("User with email ${profile.email} already exists. Please click update to update or exit")
                            binding.btn.visibility = View.GONE
                            binding.update.visibility = View.VISIBLE
                            binding.update.setOnClickListener {
                                //viewModel.updateProfile()
                                toast("Profile updated")
                            }
                        }
                    }
                }
            }
        }
    }


    // Helper function to validate user input
    private fun validateInput(): ValidateResult {
        val name = binding.name.editText?.text.toString()
        val city = binding.city.editText?.text.toString()
        val country = binding.country.editText?.text.toString()
        val email = binding.email.editText?.text.toString()


        if (name.isEmpty()) {
            binding.name.error = "Field must not be empty"
            return ValidateResult(false, null)
        }
        if (city.isEmpty()) {
            binding.city.error = "Field must not be empty"
            return ValidateResult(false, null)
        }
        if (country.isEmpty()) {
            binding.country.error = "Field must not be empty"
            return ValidateResult(false, null)
        }
        if (email.isEmpty()) {
            binding.email.error = "Field must not be empty"
            return ValidateResult(false, null)
        }

        return ValidateResult(true, Profile(name = name, country = country, city = city, email = email,image = selectedImageByteArray))
    }

    // Data class to hold validation result
    private data class ValidateResult(val isValid: Boolean, val profile: Profile? = null)


}