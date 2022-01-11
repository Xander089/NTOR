package com.example.ntor.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ntor.databinding.FragmentHomeBinding
import com.example.ntor.libraries.mapbox.LocationPermissionHelper
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.run.RunActivity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper

    @Inject
    lateinit var mapboxManager: MapboxManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions { mapboxManager.onMapReady(requireContext()) }
        initLayout()

        return binding.root
    }

    private fun initLayout() {
        binding.startRunButton.setOnClickListener {
            val intent = RunActivity.newIntent(requireContext())
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper
            .onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
    }
}