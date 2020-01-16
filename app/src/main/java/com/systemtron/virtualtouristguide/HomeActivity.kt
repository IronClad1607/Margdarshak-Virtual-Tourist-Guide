package com.systemtron.virtualtouristguide

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.systemtron.virtualtouristguide.features.kym.CaptureActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnCapture.setOnClickListener {
            val intent = Intent(this,CaptureActivity::class.java)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {

            Snackbar.make(
                findViewById(android.R.id.content),
                "Work In Progress....",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }
}
