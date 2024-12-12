package com.devgiul.mychofer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.devgiul.mychofer.presentation.ui.activitys.RideRequestActivity


class MainActivity : AppCompatActivity() {

    private lateinit var estimateButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        estimateButton = findViewById(R.id.buttonEstimateRide)

        estimateButton.setOnClickListener{
            val intent = Intent(
                this@MainActivity,
                RideRequestActivity::class.java
            )
            startActivity(intent)
        }

    }
}