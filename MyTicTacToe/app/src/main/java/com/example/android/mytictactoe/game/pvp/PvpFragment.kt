package com.example.android.mytictactoe.game.pvp

import android.graphics.drawable.Drawable
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
import com.example.android.mytictactoe.databinding.PvpFragmentBinding
import java.util.concurrent.TimeUnit

class PvpFragment : Fragment() {

    private lateinit var binding: PvpFragmentBinding

    private lateinit var viewModel: PvpViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.pvp_fragment, container, false)

        // Set up the viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = GameDatabase.getInstance(application).gameDatabaseDao
        val viewModelFactory = PvpViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PvpViewModel::class.java)
        binding.pvpViewModel = viewModel

        // Start the chronometer
        binding.cMeter.start()

        // Start the game
        game()

        // Listen for end of game flag
        viewModel.eventGameFinish.observe(
            viewLifecycleOwner,
            Observer<Boolean> { hasFinished -> if (hasFinished) gameFinished() })

        binding.lifecycleOwner = this

        // Will play again
        binding.playAgain.setOnClickListener {
            playAgain()
        }

        return binding.root
    }

    // Handles the end of game
    private fun gameFinished() {
        binding.cMeter.stop()

        // Show the time
        val elapsedMillis: Long = SystemClock.elapsedRealtime() - binding.cMeter.base
        Toast.makeText(
            activity,
            String.format(
                "Duration: %d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsedMillis),
                TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(elapsedMillis)
                )
            ), Toast.LENGTH_SHORT
        ).show()
    }


    // Will listen to any player move and update the table
    private fun game() {
        binding.Button1.setOnClickListener {
            val text = viewModel.game(0, 0)
            if (text != "err")
                binding.Button1.text = text

        }
        binding.Button2.setOnClickListener {
            val text = viewModel.game(0, 1)
            if (text != "err")
                binding.Button2.text = text
        }
        binding.Button3.setOnClickListener {
            val text = viewModel.game(0, 2)
            if (text != "err")
                binding.Button3.text = text
        }
        binding.Button4.setOnClickListener {
            val text = viewModel.game(1, 0)
            if (text != "err")
                binding.Button4.text = text
        }
        binding.Button5.setOnClickListener {
            val text = viewModel.game(1, 1)
            if (text != "err")
                binding.Button5.text = text
        }
        binding.Button6.setOnClickListener {
            val text = viewModel.game(1, 2)
            if (text != "err")
                binding.Button6.text = text
        }
        binding.Button7.setOnClickListener {
            val text = viewModel.game(2, 0)
            if (text != "err")
                binding.Button7.text = text
        }
        binding.Button8.setOnClickListener {
            val text = viewModel.game(2, 1)
            if (text != "err")
                binding.Button8.text = text
        }
        binding.Button9.setOnClickListener {
            val text = viewModel.game(2, 2)
            if (text != "err")
                binding.Button9.text = text
        }
    }

    // Resets the game
    private fun playAgain() {

        viewModel.onGameOverComplete()

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
}