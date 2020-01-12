package com.systemtron.virtualtouristguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnCapture.setOnClickListener {

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
