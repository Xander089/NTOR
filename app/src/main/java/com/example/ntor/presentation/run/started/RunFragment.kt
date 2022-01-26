package com.example.ntor.presentation.run.started

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunBinding
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.utils.Constants.PARCEL_TAG
import com.example.ntor.presentation.utils.Constants.PAUSE
import com.example.ntor.presentation.utils.Constants.PLAY
import com.example.ntor.presentation.utils.Constants.STOP_TAG
import com.example.ntor.presentation.utils.NavigationManager
import com.example.ntor.presentation.utils.dialogs.DialogFactory
import com.example.ntor.presentation.utils.dialogs.DialogFlavour
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment() {

    companion object {
        private const val ACTION_RUN_TO_COMPLETED = R.id.action_runFragment_to_runCompletedFragment
    }

    @Inject
    lateinit var mapboxManager: MapboxManager
    private lateinit var binding: FragmentRunBinding
    private val viewModel: RunFragmentViewModel by activityViewModels()

    //if run is started --> user can't go back to countdown fragment
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRunBinding.inflate(inflater, container, false)

        setupMapBox()
        initLayout()
        initObservers()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(onBackPressedCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
    }

    private fun setupMapBox() {
        mapboxManager.apply {
            setMapView(binding.mapView)

            //when new position is available -> temporarily cache it
            setMapBoxHandlePosition { latitude, longitude ->
                viewModel.insertNewPosition(latitude, longitude)
            }
            onMapReady(requireContext()) //shows the map
        }
    }


    private fun initLayout() {
        binding.apply {
            lockButton.setOnClickListener {
                toggleButtonVisibility(pauseRunButton)
                toggleButtonVisibility(stopRunButton)
            }
            stopRunButton.setOnClickListener {
                showStopRunDialog()
                viewModel.stopTimer()
                viewModel.setTimerState(TimerState.OFF)

            }
            pauseRunButton.setOnClickListener {
                handlePauseButton(viewModel.getTimerState())
            }
        }
    }

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
            val view = binding.rythmTextView
            view.text = viewModel.toMinutes(
                pacing,
                view.text.toString()
            )
        })
    }

    private fun toggleButtonVisibility(button: Button) {
        button.visibility = when (button.visibility) {
            View.VISIBLE -> View.INVISIBLE
            View.INVISIBLE -> View.VISIBLE
            else -> View.VISIBLE
        }
    }

    private fun handlePauseButton(state: TimerState) {
        when (state) {
            TimerState.ON -> pause()
            TimerState.OFF -> restart()
        }
    }

    private fun pause() {
        viewModel.stopTimer()
        viewModel.setTimerState(TimerState.OFF)
        binding.pauseRunButton.text = PLAY
        binding.pauseRunButton.setBackgroundColor(getColor(R.color.preview_fab))
    }

    private fun restart() {
        viewModel.startTimer(getTimerText())
        viewModel.setTimerState(TimerState.ON)
        binding.pauseRunButton.text = PAUSE
        binding.pauseRunButton.setBackgroundColor(getColor(R.color.orange))
    }

    private fun showStopRunDialog() {
        val dialog = DialogFactory.create(
            DialogFlavour.STOP_RUN,
            ok = {
                NavigationManager.navigateTo(
                    findNavController(),
                    ACTION_RUN_TO_COMPLETED,
                    bundleOf(PARCEL_TAG to viewModel.buildRunParcelable())
                )
            },
            cancel = {
                restart()
            }
        )
        dialog.show(parentFragmentManager, STOP_TAG)
    }

    private fun getResourceString(id: Int) = context?.resources?.getString(id)
    private fun getColor(id: Int) = context?.resources?.getColor(id, null) ?: 0
    private fun getTimerText() = binding.timerTextView.text.toString()

}