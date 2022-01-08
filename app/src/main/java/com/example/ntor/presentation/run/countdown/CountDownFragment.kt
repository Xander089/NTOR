package com.example.ntor.presentation.run.countdown

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentCountDownBinding
import com.example.ntor.databinding.FragmentRunBinding
import com.example.ntor.presentation.NavigationManager


class CountDownFragment : Fragment() {

    companion object {
        private const val ACTION = R.id.action_countDownFragment_to_runFragment
    }

    private val viewModel = CountDownVIewModel()
    private lateinit var binding: FragmentCountDownBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCountDownBinding.inflate(inflater, container, false)

        binding.startNowTextView.setOnClickListener {
            viewModel.stopTimer()
            NavigationManager.navigateTo(findNavController(), ACTION)
        }

        initObserver()

        viewModel.startTimer()

        return binding.root
    }

    private fun initObserver() {
        viewModel.timerText.observe(requireActivity(), { seconds ->
            binding.countDownTextView.text = seconds
            navigateToRunFragment(seconds.toInt())
        })
    }

    private fun navigateToRunFragment(time: Int) {
        if (time == 0) {
            NavigationManager.navigateTo(findNavController(), ACTION)
        }
    }

}