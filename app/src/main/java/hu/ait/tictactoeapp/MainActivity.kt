package hu.ait.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import hu.ait.tictactoeapp.databinding.ActivityMainBinding
import hu.ait.tictactoeapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btndelete.setOnClickListener() {
            binding.ticTacToe.resetGame()
        }
        resetTimer()
    }

    fun setMessage(nextText: String) {
        binding.tvMessage.text = nextText
    }

    fun resetTimer() {
        binding.timer.setBase(SystemClock.elapsedRealtime())
        binding.timer.start()
    }

    fun stopTimer() {
        binding.timer.stop()
    }

    //for hw 1
    fun flagMode(): Boolean {
        return binding.cbTest.isChecked
    }

}