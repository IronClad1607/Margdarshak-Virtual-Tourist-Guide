package com.systemtron.virtualtouristguide.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.systemtron.virtualtouristguide.HomeActivity
import com.systemtron.virtualtouristguide.R
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {

    var verficationCode: String = ""
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(this@PhoneLoginActivity, "Verification Complete", Toast.LENGTH_LONG)
                    .show()
                signInWithPhone(p0)

                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)

            }

            override fun onVerificationFailed(p0: FirebaseException) {

                Log.d("PUI",p0.localizedMessage)
                Toast.makeText(this@PhoneLoginActivity, "${p0.localizedMessage}", Toast.LENGTH_LONG)
                    .show()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)


                Toast.makeText(
                    this@PhoneLoginActivity,
                    "Code Sent to +91${etPhone.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        btnSubmit.setOnClickListener {
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber("+91${etPhone.text}", 60, TimeUnit.SECONDS, this, callback)
        }


    }

    private fun signInWithPhone(p0: PhoneAuthCredential) {
        auth.signInWithCredential(p0)
            .addOnCompleteListener {

            }
            .addOnFailureListener {

            }
            .addOnSuccessListener {

            }
    }
}
