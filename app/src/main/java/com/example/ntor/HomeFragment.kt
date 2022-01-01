package com.example.ntor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ntor.databinding.FragmentHomeBinding
import com.example.ntor.mapbox.LocationPermissionHelper
import com.example.ntor.mapbox.MapboxManager
import java.lang.ref.WeakReference


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private val mapboxManager = MapboxManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions {
            mapboxManager.onMapReady(requireContext())
        }


        return binding.root
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