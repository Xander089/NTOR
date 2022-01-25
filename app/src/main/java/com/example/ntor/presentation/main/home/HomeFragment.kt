package com.example.ntor.presentation.main.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ntor.databinding.FragmentHomeBinding
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.run.RunActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var mapboxManager: MapboxManager

    private val requestMultiplePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.map { if (it.value) 1 else 0 }.sum() == 2
            if (allGranted) {
                mapboxManager.onMapReady(requireContext())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        onMapReady()
        initLayout()

        return binding.root
    }

    private fun onMapReady() {
        if (areLocationPermissionsGranted()) {
            mapboxManager.onMapReady(requireContext())
        }
    }

    private fun checkPermissions(
        shouldShowRequestPermissionRationale: Boolean,
        action: () -> Unit
    ) {
        when {
            areLocationPermissionsGranted() -> action()
            shouldShowRequestPermissionRationale -> {}
            else -> launchAskPermissions()
        }
    }

    private fun areLocationPermissionsGranted() =
        isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED

    private fun launchAskPermissions() {
        requestMultiplePermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun initLayout() {
        binding.apply {
            startRunButton.setOnClickListener {
                val intent = RunActivity.newIntent(requireContext())
                startActivity(intent)
            }
            locationButton.setOnClickListener {
                val permissionsNotGranted = !areLocationPermissionsGranted()
                if(permissionsNotGranted){
                    checkPermissions(false) {
                        mapboxManager.onMapReady(requireContext())
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
    }
}