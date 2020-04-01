package com.systemtron.virtualtouristguide.features.kym

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.systemtron.virtualtouristguide.HomeActivity
import com.systemtron.virtualtouristguide.R
import com.systemtron.virtualtouristguide.login.LoginActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    var arch: String? = null
    var history: String? = null
    var fee: String? = null
    var vt: String? = null

    var englishGermanTranslator: FirebaseTranslator? = null

    var englishHindiTranslator: FirebaseTranslator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbaar)
        supportActionBar?.title = null

        val name = intent.getStringExtra("pass")

        tvMName.text = name

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = firebaseDatabase.reference

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                showData(p0)
            }

        })

        val optionGerman = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.DE)
            .build()

        val optionHindi = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(FirebaseTranslateLanguage.EN)
            .setTargetLanguage(FirebaseTranslateLanguage.HI)
            .build()

        englishGermanTranslator =
            FirebaseNaturalLanguage.getInstance().getTranslator(optionGerman)

        englishHindiTranslator =
            FirebaseNaturalLanguage.getInstance().getTranslator(optionHindi)

        englishGermanTranslator!!.downloadModelIfNeeded()
            .addOnCompleteListener {

            }
            .addOnFailureListener {

            }
            .addOnSuccessListener {

            }

        englishHindiTranslator!!.downloadModelIfNeeded()
            .addOnCompleteListener {

            }
            .addOnFailureListener {

            }
            .addOnSuccessListener {

            }


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
        R.id.langGER -> {
            translateGerman(arch, history, fee, vt)
            true
        }

        R.id.langHIN -> {
            translateHindi(arch, history, fee, vt)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun translateHindi(arch: String?, history: String?, fee: String?, vt: String?) {
        englishHindiTranslator!!.translate(arch!!).addOnSuccessListener {
            runOnUiThread {
                tvArch.text = it
            }
        }

        englishHindiTranslator!!.translate(history!!).addOnSuccessListener {
            runOnUiThread {
                tvHistory.text = it
            }
        }

        englishHindiTranslator!!.translate(fee!!).addOnSuccessListener {
            runOnUiThread {
                tvEntry.text = it
            }
        }

        englishHindiTranslator!!.translate(vt!!).addOnSuccessListener {
            runOnUiThread {
                tvVT.text = it
            }
        }
    }

    private fun translateGerman(arch: String?, history: String?, fee: String?, vt: String?) {
        englishGermanTranslator!!.translate(arch!!).addOnSuccessListener {
            runOnUiThread {
                tvArch.text = it
            }
        }

        englishGermanTranslator!!.translate(history!!).addOnSuccessListener {
            runOnUiThread {
                tvHistory.text = it
            }
        }

        englishGermanTranslator!!.translate(fee!!).addOnSuccessListener {
            runOnUiThread {
                tvEntry.text = it
            }
        }

        englishGermanTranslator!!.translate(vt!!).addOnSuccessListener {
            runOnUiThread {
                tvVT.text = it
            }
        }
    }

    private fun showData(snapshot: DataSnapshot) {
        for (ds in snapshot.children) {
            arch = ds.child(tvMName.text as String).child("arch").value.toString()
            history = ds.child(tvMName.text.toString()).child("history").value.toString()
            fee = ds.child(tvMName.text.toString()).child("fee").value.toString()
            vt = ds.child(tvMName.text.toString()).child("visiting time").value.toString()

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
