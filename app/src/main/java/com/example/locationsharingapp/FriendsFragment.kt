package com.example.locationsharingapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.locationsharingapp.adapter.UserAdapter
import com.example.locationsharingapp.databinding.FragmentFriendsBinding
import com.example.locationsharingapp.viewmodel.AthenticationViewModel
import com.example.locationsharingapp.viewmodel.FireStoreViewModel
import com.example.locationsharingapp.viewmodel.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding
    private lateinit var fireStoreViewModel: FireStoreViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var authenticationViewModel: AthenticationViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(requireContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater, container, false)

        fireStoreViewModel = ViewModelProvider(this).get(FireStoreViewModel::class.java)
        authenticationViewModel = ViewModelProvider(this).get(AthenticationViewModel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationViewModel.initializeFusedLocationClient(fusedLocationClient)

        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Permission is already granted
            getLocation()
        }

        userAdapter = UserAdapter(emptyList())
        binding.userRV.apply {
            userAdapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

            fetchUsers()



        binding.locationBtn.setOnClickListener{

        }





        return binding.root



    }

    private fun fetchUsers() {
        fireStoreViewModel.getAllUsers(requireContext()) {
            userAdapter.updateData(it)
        }
    }
    private fun getLocation() {
        LocationViewModel.getLastLocation(requireContext()) {
            // Save location to Firestore for the current user
            authenticationViewModel.getCurrentUserId().let { userId ->
                fireStoreViewModel.updateUserLocation(requireContext(),userId, it)
            }
        }
    }

    }


