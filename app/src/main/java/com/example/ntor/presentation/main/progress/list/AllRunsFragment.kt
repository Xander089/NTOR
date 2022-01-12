package com.example.ntor.presentation.main.progress.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ntor.R
import com.example.ntor.core.entities.Run
import com.example.ntor.databinding.FragmentAllRunsBinding
import com.example.ntor.presentation.NavigationManager
import com.example.ntor.presentation.main.progress.detail.RunDetailFragment


class AllRunsFragment : Fragment() {

    companion object {
        private const val ACTION = R.id.action_allRunsFragment_to_runDetailFragment
    }

    private lateinit var binding: FragmentAllRunsBinding
    private val viewModel: AllRunsFragmentViewModel by activityViewModels()
    private lateinit var runAdapter: RunAdapter
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllRunsBinding.inflate(inflater, container, false)
        buildAdapter()
        initLayout()
        initObservers()

        return binding.root
    }

    private fun initLayout(){
        binding.apply {
            runList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = runAdapter
            }
        }
    }

    private fun buildAdapter() {
        runAdapter = RunAdapter(
            openDetail = { date ->
                NavigationManager.navigateTo(
                    findNavController(),
                    ACTION,
                    bundleOf(RunDetailFragment.RUN_DATE to date)
                )
            },
        onSwipe = { date ->
            viewModel.deleteRunByDate(date)})

        mItemTouchHelper = ItemTouchHelper(SwipeHelperCallback(runAdapter))
        mItemTouchHelper?.attachToRecyclerView(binding.runList)
    }

    private fun initObservers() {
        viewModel.runs.observe(requireActivity(), { runs ->
            runAdapter.updateRuns(runs)

            binding.apply {
                activitiesTextView.text = viewModel.formatActivities(runs)
                distanceTextView.text = viewModel.formatDistance(runs)
                timeTextView.text = viewModel.formatTime(runs)
            }
        })
    }


}