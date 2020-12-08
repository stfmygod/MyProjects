package com.example.android.mytictactoe.startPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.mytictactoe.R
import com.example.android.mytictactoe.databinding.StartPageBinding

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : StartPageBinding = DataBindingUtil.inflate(inflater, R.layout.start_page, container, false)

        // Go to PVP game
        binding.pvpButton.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToPvpFragment())
        }

        // Go to PVM game
        binding.pvmButton.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToPvmFragment())
        }

        // Go to statistics
        binding.statisticsButton.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToStatisticsFragment())
        }

        return binding.root
    }
}