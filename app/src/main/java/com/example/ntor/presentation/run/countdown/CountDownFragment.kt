package com.example.ntor.presentation.run.countdown

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentCountDownBinding
import com.example.ntor.presentation.utils.Constants.STANDARD_SECONDS
import com.example.ntor.presentation.utils.NavigationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CountDownFragment : Fragment() {

    companion object {
        private const val ACTION = R.id.action_countDownFragment_to_runFragment
    }

    @Inject
    lateinit var viewModel: CountDownViewModel

    @Inject
    lateinit var player: CountDownAudioPlayer

    private lateinit var binding: FragmentCountDownBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCountDownBinding.inflate(inflater, container, false)

        initLayout()
        initObserver()

        player.start(requireContext(), R.raw.countdown)
        viewModel.startTimer()

        return binding.root
    }

    private fun initLayout() {
        binding.apply {
            startNowTextView.setOnClickListener {
                viewModel.disposeTimer()
                player.release()
                NavigationManager.navigateTo(findNavController(), ACTION)
            }
            addSecondsButton.setOnClickListener {
                viewModel.addSeconds(
                    STANDARD_SECONDS,
                    countDownTextView.text.toString()
                )
            }

        }

    }

    private fun initObserver() {
        viewModel.timerText.observe(requireActivity(), { seconds ->
            binding.countDownTextView.text = seconds
            navigateToRunFragment(seconds.toInt())
        })
    }

    private fun navigateToRunFragment(time: Int) {
        if (time == 0) {
            viewModel.disposeTimer()
            NavigationManager.navigateTo(findNavController(), ACTION)
        }
    }

    override fun onResume() {
        super.onResume()
        player.start(requireContext(), R.raw.countdown)
    }

    override fun onStop() {
        super.onStop()
        viewModel.disposeTimer()
        player.release()
    }

}