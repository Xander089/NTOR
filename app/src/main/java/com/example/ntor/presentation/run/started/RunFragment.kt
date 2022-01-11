package com.example.ntor.presentation.run.started

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunBinding
import com.example.ntor.libraries.mapbox.LocationPermissionHelper
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.NavigationManager
import com.example.ntor.presentation.RunParcelable
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment(), StopRunDialog.DialogListener {

    companion object {
        private const val ACTION_RUN_TO_COMPLETED = R.id.action_runFragment_to_runCompletedFragment
        private const val PAUSE = "PAUSE"
        private const val PLAY = "PLAY"
    }


    //Significant Motion sensor: triggered to detect when user is moving
    private lateinit var sensorManager: SensorManager
    private var significantMotionSensor: Sensor? = null
    private val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            viewModel.setUserMotionState(UserMotion.MOVING)
        }
    }

    @Inject
    lateinit var mapboxManager : MapboxManager
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var binding: FragmentRunBinding
    private val viewModel: RunFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRunBinding.inflate(inflater, container, false)

        setupMapBox()
        initMotionSensor()
        initLayout()
        initObservers()

        return binding.root
    }

    private fun setupMapBox(){
        mapboxManager.setMapBoxHandlePosition{ latitude, longitude ->
            viewModel.insertNewPosition(latitude, longitude)
        }
        mapboxManager.setMapView(binding.mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(requireActivity()))
        locationPermissionHelper.checkPermissions { mapboxManager.onMapReady(requireContext()) }
    }

    private fun initMotionSensor() {
        sensorManager =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        significantMotionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
    }

    private fun initLayout() {
        binding.apply {
            lockButton.setOnClickListener {
                toggleButtonVisibility(pauseRunButton)
                toggleButtonVisibility(stopRunButton)
            }
            stopRunButton.setOnClickListener {
                viewModel.stopTimer()
                viewModel.setTimerState(TimerState.OFF)
                //showStopRunDialog()
                NavigationManager.navigateTo(
                    findNavController(),
                    ACTION_RUN_TO_COMPLETED,
                    bundleOf("parcel" to viewModel.buildRunParcelable())
                )

            }
            pauseRunButton.setOnClickListener {
                handlePauseButton(pauseRunButton)
            }
        }
    }

    private fun showStopRunDialog() {
        StopRunDialog(
            getResourceString(R.string.stop_run_dialog_message),
            getResourceString(R.string.ok),
            getResourceString(R.string.cancel)
        )
            .show(parentFragmentManager, "TAG")
    }

    private fun getResourceString(id: Int) = requireActivity().resources.getString(id)

    private fun initObservers() {
        viewModel.timerText.observe(this, { time ->
            binding.timerTextView.text = time
        })

        viewModel.points.observe(this, { points ->
            viewModel.updateDistance(points)
        })

        viewModel.distance.observe(this, { distance ->
            viewModel.updateCalories(distance)
            viewModel.updatePacing(getTimerText(), distance)
            binding.distanceTextView.text = viewModel.formatDouble(distance)
        })

        viewModel.calories.observe(this, { calories ->
            binding.caloriesTextView.text = viewModel.formatDouble(calories)
        })
        viewModel.pacing.observe(this, { pacing ->
            binding.rythmTextView.text = viewModel.toMinutes(pacing)
        })
    }

    private fun handlePauseButton(button: Button) {

        val buttonText = button.text.toString()

        if (buttonText == PAUSE) {
            viewModel.stopTimer()
            viewModel.setTimerState(TimerState.OFF)
            button.text = PLAY
            button.setBackgroundColor(requireActivity().resources.getColor(R.color.green, null))
        } else {
            viewModel.startTimer(getTimerText())
            viewModel.setTimerState(TimerState.ON)
            button.text = PAUSE
            button.setBackgroundColor(requireActivity().resources.getColor(R.color.orange, null))
        }

    }

    private fun getTimerText() = binding.timerTextView.text.toString()

    private fun toggleButtonVisibility(button: Button) {
        if (button.visibility == View.VISIBLE) {
            button.visibility = View.INVISIBLE
        } else {
            button.visibility = View.VISIBLE
        }
    }


    //LIFE CYCLE CALLBACKS

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


    override fun onDialogPositiveClick() {

    }

    override fun onDialogNegativeClick() {
    }


}