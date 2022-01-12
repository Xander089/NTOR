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
import com.example.ntor.presentation.RunParcelable
import com.example.ntor.presentation.camera.CameraActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RunCompletedFragment : Fragment() {

    companion object {
        const val PARCEL = "parcel"
    }

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
                finishRunActivity()
            }

            closeButton.setOnClickListener {
                finishRunActivity()
            }

            populateTextViews()

        }
    }

    private fun finishRunActivity() = requireActivity().finish()

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

    override fun onDestroy() {
        super.onDestroy()
        mapboxManager.onCameraTrackingDismissed()
    }

}