package com.example.ntor.presentation.run.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunCompletedBinding
import com.example.ntor.libraries.mapbox.LocationPermissionHelper
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.NavigationManager
import com.example.ntor.presentation.camera.CameraActivity
import kotlinx.android.synthetic.main.fragment_run.*
import java.lang.ref.WeakReference
import java.util.*


class RunCompletedFragment : Fragment() {

    companion object {
        const val TIME = "time"
        const val PACING = "pacing"
        const val CALORIES = "calories"
        const val DISTANCE = "distance"
    }

    private lateinit var binding: FragmentRunCompletedBinding
    private val mapboxManager = MapboxManager()
    private val viewModel: RunCompletedFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRunCompletedBinding.inflate(inflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        viewModel.setupRoute()
        initLayout()
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        viewModel.points.observe(requireActivity(), { points ->
            mapboxManager.onRouteReady(requireContext(), points)
        })
    }

    private fun initLayout() {
        binding.apply {
            cameraButton.setOnClickListener {
                startActivity(CameraActivity.newIntent(requireContext()))
            }

            saveRunButton.setOnClickListener {
//                viewModel.createRun()
                requireActivity().finish()
            }

            closeButton.setOnClickListener {
                requireActivity().finish()
            }

            distanceText.text = arguments?.getString(DISTANCE).orEmpty()
            runDurationText.text = arguments?.getString(TIME).orEmpty()
            startTimeText.text = Date().toString()
            pacingText.text = arguments?.getString(PACING).orEmpty()
            caloriesText.text = arguments?.getString(CALORIES).orEmpty()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
    }

}