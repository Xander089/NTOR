package com.example.ntor.presentation.run.started

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunBinding
import com.example.ntor.libraries.mapbox.LocationPermissionHelper
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.NavigationManager
import java.lang.ref.WeakReference
import java.util.*


class RunFragment : Fragment() {

    companion object {
        private const val ACTION = R.id.action_runFragment_to_runCompletedFragment
    }

    private val viewModel: RunFragmentViewModel by activityViewModels()

    //Significant Motion sensor: triggered to detect when user is moving
    private lateinit var sensorManager: SensorManager
    private var significantMotionSensor: Sensor? = null
    private val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            viewModel.setUserMotionState(UserMotion.MOVING)
        }
    }

    private lateinit var binding: FragmentRunBinding
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private val mapboxManager = MapboxManager(
        handlePositionReading = { latitude, longitude ->
            viewModel.insertNewPosition(latitude, longitude)

            val time = Date().time
            if(time  - viewModel.lastInsertion > 2000){
                binding.caloriesTextView.text = latitude.toString()
            }

        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sensorManager =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        significantMotionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)

        binding = FragmentRunBinding.inflate(inflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions { mapboxManager.onMapReady(requireContext()) }

        initLayout()
        initObservers()

        return binding.root
    }

    private fun initLayout() {
        binding.apply {
            lockButton.setOnClickListener {
                toggleButtonVisibility(pauseRunButton)
                toggleButtonVisibility(stopRunButton)
            }
            stopRunButton.setOnClickListener {
                NavigationManager.navigateTo(findNavController(), ACTION)
            }
        }
    }

    private fun toggleButtonVisibility(button: Button) {
        if (button.visibility == View.VISIBLE) {
            button.visibility = View.INVISIBLE
        } else {
            button.visibility = View.VISIBLE
        }
    }

    private fun initObservers() {
        viewModel.latestRun.observe(requireActivity(), {
            Log.v("idd", it.time.toString())
        })
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

    override fun onResume() {
        super.onResume()
        significantMotionSensor?.let { sensor ->
            sensorManager.requestTriggerSensor(triggerEventListener, sensor)
        }
    }

    override fun onPause() {
        super.onPause()
        significantMotionSensor?.let { sensor ->
            sensorManager.cancelTriggerSensor(triggerEventListener, sensor)
        }
    }

}