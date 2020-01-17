package com.systemtron.virtualtouristguide.features.kym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector
import com.systemtron.virtualtouristguide.HomeActivity
import com.systemtron.virtualtouristguide.R
import com.systemtron.virtualtouristguide.features.kym.model.Landmark
import com.systemtron.virtualtouristguide.login.LoginActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbaar)
        supportActionBar?.title = null

        val name = intent.getStringExtra("pass")

        tvMName.text = name


        val firebaseDatabse = FirebaseDatabase.getInstance()
        val myRef = firebaseDatabse.reference

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                showData(p0)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.btnSignout -> {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_LONG).show()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun showData(snapshot: DataSnapshot) {
        for (ds in snapshot.children) {
            val arch = ds.child(tvMName.text as String).child("arch").value.toString()
            val history = ds.child(tvMName.text.toString()).child("history").value.toString()
            val fee = ds.child(tvMName.text.toString()).child("fee").value.toString()
            val vt = ds.child(tvMName.text.toString()).child("visiting time").value.toString()

            runOnUiThread {
                tvArch.text = arch
                tvHistory.text = history
                tvEntry.text = fee
                tvVT.text = vt
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
