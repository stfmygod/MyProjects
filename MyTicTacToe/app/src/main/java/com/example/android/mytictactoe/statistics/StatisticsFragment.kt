package com.example.android.mytictactoe.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.mytictactoe.R
import com.example.android.mytictactoe.database.GameDatabase
import com.example.android.mytictactoe.databinding.StatisticsFragmentBinding


class StatisticsFragment : Fragment() {

    private lateinit var binding: StatisticsFragmentBinding

    private lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.statistics_fragment, container, false)
        binding.lifecycleOwner = this

        // Set up the viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).gameDatabaseDao
        val viewModelFactory = StatisticsViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StatisticsViewModel::class.java)
        binding.statisticsViewModel = viewModel


        // Wait until the database was read
        viewModel.databaseIsDone.observe(
            viewLifecycleOwner,
            Observer { isDone -> if (isDone) initTextView() })

        return binding.root
    }

    private fun initTextView() {
        val allPvpGames = viewModel.getAllPVPGames()
        val allPvpGamesWon = viewModel.getAllPVPGamesWonByX()
        val allPvpGamesTie = viewModel.getAllPVPGamesTie()

        val allPvmGames = viewModel.getAllPVMGames()
        val allPvmGamesWon = viewModel.getAllPVMGamesWonByX()
        val allPvmGamesTie = viewModel.getAllPVMGamesTie()

        binding.statisticsText.text = String.format(
            getString(R.string.statisticsString),
            allPvpGames,
            allPvpGamesWon,
            allPvpGamesTie,
            allPvmGames,
            allPvmGamesWon,
            allPvmGamesTie
        )
    }
}