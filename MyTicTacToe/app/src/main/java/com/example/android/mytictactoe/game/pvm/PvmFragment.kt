package com.example.android.mytictactoe.game.pvm

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.mytictactoe.R
import com.example.android.mytictactoe.database.GameDatabase
import com.example.android.mytictactoe.databinding.PvmFragmentBinding
import java.util.concurrent.TimeUnit

class PvmFragment : Fragment() {

    private lateinit var binding: PvmFragmentBinding

    private lateinit var viewModel: PvmViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.pvm_fragment, container, false)

        // Set the viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).gameDatabaseDao
        val viewModelFactory = PvmViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PvmViewModel::class.java)
        binding.pvmViewModel = viewModel

        // Start the chronometer
        binding.cMeter.start()

        binding.lifecycleOwner = this

        // Start the game
        game()

        // Checks if the game has finished
        viewModel.eventGameFinish.observe(
            viewLifecycleOwner,
            Observer<Boolean> { hasFinished -> if (hasFinished) gameFinished() })

        // Resets the game
        binding.playAgain.setOnClickListener {
            playAgain()
        }


        return binding.root
    }

    // End the game
    private fun gameFinished() {
        binding.cMeter.stop()

        // Show the time
        val elapsedMillis: Long = SystemClock.elapsedRealtime() - binding.cMeter.base
        Toast.makeText(
            activity, String.format(
                "Duration: %d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(elapsedMillis)
                )
            ), Toast.LENGTH_SHORT
        ).show()

    }

    // Will listen to any button presses, play the game and refresh the table
    private fun game() {
        binding.Button1.setOnClickListener {
            viewModel.game(0, 0)
            refreshTable()
        }
        binding.Button2.setOnClickListener {
            viewModel.game(0, 1)
            refreshTable()
        }
        binding.Button3.setOnClickListener {
            viewModel.game(0, 2)
            refreshTable()
        }
        binding.Button4.setOnClickListener {
            viewModel.game(1, 0)
            refreshTable()
        }
        binding.Button5.setOnClickListener {
            viewModel.game(1, 1)
            refreshTable()
        }
        binding.Button6.setOnClickListener {
            viewModel.game(1, 2)
            refreshTable()
        }
        binding.Button7.setOnClickListener {
            viewModel.game(2, 0)
            refreshTable()
        }
        binding.Button8.setOnClickListener {
            viewModel.game(2, 1)
            refreshTable()
        }
        binding.Button9.setOnClickListener {
            viewModel.game(2, 2)
            refreshTable()
        }
    }

    // Resets the game
    private fun playAgain() {

        viewModel.onGameRestart()

        binding.Button1.text = ""
        binding.Button2.text = ""
        binding.Button3.text = ""
        binding.Button4.text = ""
        binding.Button5.text = ""
        binding.Button6.text = ""
        binding.Button7.text = ""
        binding.Button8.text = ""
        binding.Button9.text = ""

        binding.cMeter.base = SystemClock.elapsedRealtime()

        binding.cMeter.start()
    }

    // Refreshes table
    private fun refreshTable() {
        val table = viewModel.getMatrix()

        binding.Button1.text = table[0][0]
        binding.Button2.text = table[0][1]
        binding.Button3.text = table[0][2]
        binding.Button4.text = table[1][0]
        binding.Button5.text = table[1][1]
        binding.Button6.text = table[1][2]
        binding.Button7.text = table[2][0]
        binding.Button8.text = table[2][1]
        binding.Button9.text = table[2][2]
    }


}