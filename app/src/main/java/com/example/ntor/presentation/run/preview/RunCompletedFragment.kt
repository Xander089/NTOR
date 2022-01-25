package com.example.ntor.presentation.run.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ntor.databinding.FragmentRunCompletedBinding
import com.example.ntor.libraries.mapbox.MapboxManager
import com.example.ntor.presentation.utils.RunParcelable
import com.example.ntor.presentation.camera.CameraActivity
import com.example.ntor.presentation.utils.Constants.PARCEL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RunCompletedFragment : Fragment() {


    private lateinit var binding: FragmentRunCompletedBinding

    @Inject
    lateinit var mapboxManager : MapboxManager

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

    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
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
                createRun()
                requireActivity().finish()
            }

            closeButton.setOnClickListener {
                requireActivity().finish()
            }

            populateTextViews()

        }
    }

    private fun createRun() =
        getRunParcel()?.let {
            viewModel.createRun(it)
        }


    private fun getRunParcel() = arguments?.getParcelable<RunParcelable>(PARCEL)

    private fun populateTextViews() {
        getRunParcel().let { runParcel ->
            binding.apply {
                distanceText.text = viewModel.formatDistance(runParcel?.distance ?: 0.0)
                runDurationText.text = viewModel.formatTime(runParcel?.time ?: 0)
                startTimeText.text = viewModel.formatDate(runParcel?.date ?: 0L)
                pacingText.text = viewModel.formatPacing(runParcel?.pacing ?: 0.0)
                caloriesText.text = viewModel.formatCalories(runParcel?.calories ?: 0.0)
            }
        }
    }

}