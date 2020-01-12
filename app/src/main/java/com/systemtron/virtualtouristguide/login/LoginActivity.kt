package com.systemtron.virtualtouristguide.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.systemtron.virtualtouristguide.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnPhone.setOnClickListener {
            val intent = Intent(applicationContext, PhoneLoginActivity::class.java)
            startActivity(intent)
        }

    }
}
