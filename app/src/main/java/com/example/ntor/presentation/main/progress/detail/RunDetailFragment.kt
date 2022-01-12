package com.example.ntor.presentation.main.progress.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunDetailBinding
import com.example.ntor.libraries.mapbox.MapboxManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RunDetailFragment : Fragment() {

    companion object {
        const val RUN_DATE = "run_date"
    }

    @Inject
    lateinit var mapboxManager: MapboxManager

    @Inject
    lateinit var viewModel: RunDetailFragmentViewModel //by activityViewModels()
    private lateinit var binding: FragmentRunDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        binding = FragmentRunDetailBinding.inflate(layoutInflater, container, false)
        mapboxManager.setMapView(binding.mapView)
        viewModel.setupDetailRun(getDateArgument())
        initLayout()
        initObservers()

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.releaseLiveData()
    }

    private fun initObservers() {
        viewModel.run?.observe(requireActivity(), { run ->
            binding.apply {
                distanceText.text = viewModel.formatDistance(run.distance)
                avgPacingText.text = viewModel.formatPacing(run.pacing)
                timeText.text = viewModel.formatTime(run.time)
                dateText.text = viewModel.formatDate(run.date)
            }
        })

        viewModel.runId?.observe(requireActivity(),{ runId ->
            runId?.let {
                viewModel.setRoute(runId)    
            }
            
        })

        viewModel.points?.observe(requireActivity(), { points ->
                mapboxManager.onRouteReady(requireContext(), points)
        })

    }

    private fun initLayout() {
        binding.apply {
            (activity as AppCompatActivity?)!!.setSupportActionBar(topAppBar)
        }
    }

    private fun getDateArgument() = arguments?.getLong(RUN_DATE) ?: 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

}