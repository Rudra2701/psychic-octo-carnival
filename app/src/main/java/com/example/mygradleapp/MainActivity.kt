package com.example.mygradleapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.example.mygradleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var sessionStart: Long = 0
    private var totalTime: Long = 0
    private val handler = Handler(Looper.getMainLooper())

    private val updateRunnable = object : Runnable {
        override fun run() {
            val currentTime = System.currentTimeMillis()
            val sessionTime = (currentTime - sessionStart) / 1000
            binding.screenTimeText.text = "Screen Time: ${sessionTime}s"
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    Text("Hello Compose")
                }
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.screenTimeText.text = "App Opened"

        sessionStart = System.currentTimeMillis()
        handler.post(updateRunnable)
    }

    override fun onPause() {
        super.onPause()
        totalTime += System.currentTimeMillis() - sessionStart
        handler.removeCallbacks(updateRunnable)
    }

    override fun onResume() {
        super.onResume()
        sessionStart = System.currentTimeMillis()
        handler.post(updateRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        totalTime += System.currentTimeMillis() - sessionStart
        handler.removeCallbacks(updateRunnable)
        // Log total screen time if needed
    }

}
