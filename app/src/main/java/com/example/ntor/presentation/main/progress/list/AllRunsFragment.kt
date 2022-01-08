package com.example.ntor.presentation.main.progress.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ntor.R
import com.example.ntor.core.entities.Run
import com.example.ntor.databinding.FragmentAllRunsBinding


class AllRunsFragment : Fragment() {

    private lateinit var binding: FragmentAllRunsBinding
    private val viewModel : AllRunsFragmentViewModel by activityViewModels()
    private lateinit var runAdapter : RunAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAllRunsBinding.inflate(inflater,container, false)
        buildAdapter()

        binding.apply {
           runList.apply {
               layoutManager = LinearLayoutManager(requireContext())
               adapter = runAdapter
           }
        }

        initObservers()

        return binding.root
    }

    private fun buildAdapter() {
        runAdapter = RunAdapter(mutableListOf<Run>())
    }

    private fun initObservers() {
        viewModel.runs.observe(requireActivity(),{
            runAdapter.updateRuns(it)
        })
    }


}