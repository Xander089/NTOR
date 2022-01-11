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


class RunDetailFragment : Fragment() {

    companion object {
         const val RUN_DATE = "run_date"
    }

    private val viewModel: RunDetailFragmentViewModel by activityViewModels()
    private lateinit var binding: FragmentRunDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRunDetailBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)
        initLayout()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
    }

    private fun initLayout() {
        binding.apply {
            activitiesTextView.text = arguments?.getLong(RUN_DATE).toString()
            (activity as AppCompatActivity?)!!.setSupportActionBar(topAppBar)
        }
    }

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