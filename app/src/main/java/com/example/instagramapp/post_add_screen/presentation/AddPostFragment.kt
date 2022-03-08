package com.example.instagramapp.post_add_screen.presentation

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.instagramapp.app.network.models.PostDto
import com.example.instagramapp.app.utils.Cons.Companion.LOCATION_PERMISSION_REQ_CODE
import com.example.instagramapp.app.utils.toImage
import com.example.instagramapp.databinding.AddPostFragmentBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private val binding: AddPostFragmentBinding by lazy(LazyThreadSafetyMode.NONE) {
        AddPostFragmentBinding.inflate(layoutInflater)
    }
    private val fusedLocationClient: FusedLocationProviderClient by lazy(LazyThreadSafetyMode.NONE) {
        LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private val progressDialog: ProgressDialog by lazy(LazyThreadSafetyMode.NONE) {
        ProgressDialog(requireContext())
    }

    private val viewModel: AddPostViewModel by viewModels()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var image: ParseFile? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressDialog.setTitle("Progress Bar")
        progressDialog.setMessage("Application is loading, please wait")
        progressDialog.setCancelable(false)

        viewModel.postResponse.observe(viewLifecycleOwner) { response ->
            if (response?.isSuccessful == true) {
                zeroingData()
                viewVisibility(false)
                showToast("The post has been uploaded successfully!")
            }
        }
        binding.pickLacationBtn.setOnClickListener { getCurrentLocation() }
        binding.pickImageBtn.setOnClickListener { getImage() }

        binding.createPostBtn.setOnClickListener {
            binding.apply {
                when {

                    TextUtils.isEmpty(title.text) -> title.error = "Firstname name is required!"

                    TextUtils.isEmpty(description.text) -> description.error =
                        "Firstname name is required!"

                    image == null -> showToast("Upload a photo!")

                    latitude == 0.0 -> showToast("Specify the location!")

                    longitude == 0.0 -> showToast("Specify the location!")

                    else -> {
                        val post = PostDto(
                            personId = ParseUser.getCurrentUser().objectId,
                            username = ParseUser.getCurrentUser().username,
                            title = title.text.toString(),
                            description = description.text.toString(),
                            image = image!!.toImage(),
                            longitude = longitude,
                            latitude = latitude
                        )
                        viewModel.createNewPost(postDto = post)
                        viewVisibility(true)
                    }
                }
            }
        }
    }

    private fun getCurrentLocation() {
        viewVisibility(true)
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE)
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            latitude = location.latitude
            longitude = location.longitude
            showToast("Location uploaded successfully!")
            viewVisibility(false)
        }
            .addOnFailureListener {
                showToast("Failed on getting current location")
                viewVisibility(false)
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQ_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    showToast("You need to grant permission to access location")
                }
            }
        }
    }

    private fun getImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {
            if (resultCode == Activity.RESULT_OK) {
                viewVisibility(true)
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    data.data)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                val parseFile = ParseFile("image.png", byteArray)

                parseFile.saveInBackground(SaveCallback {
                    if (it == null) {
                        viewVisibility(false)
                        showToast("Image is saved!")
                        image = parseFile
                    } else {
                        viewVisibility(false)
                        showToast(it.message!!)

                    }
                })
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
            .show();
    }

    private fun viewVisibility(status: Boolean) {
        if (status) progressDialog.show()
        else progressDialog.dismiss()
    }

    private fun zeroingData() {
        binding.title.setText("")
        binding.description.setText("")
        latitude = 0.0
        longitude = 0.0
        image = null
        viewModel.finish()
    }
}

