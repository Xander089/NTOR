package com.example.ntor.presentation.run.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ntor.R
import com.example.ntor.databinding.FragmentRunCompletedBinding
import com.example.ntor.presentation.NavigationManager
import com.example.ntor.presentation.camera.CameraActivity


class RunCompletedFragment : Fragment() {

    private lateinit var binding: FragmentRunCompletedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunCompletedBinding.inflate(inflater, container, false)

        binding.apply {
            cameraButton.setOnClickListener {
                val intent = CameraActivity.newIntent(requireContext())
                startActivity(intent)
            }

            confirmActivityButton.setOnClickListener {

            }
        }

        return binding.root
    }



  }