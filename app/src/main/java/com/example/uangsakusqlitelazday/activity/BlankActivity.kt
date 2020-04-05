package com.example.uangsakusqlitelazday.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uangsakusqlitelazday.R

class BlankActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blank)

        supportActionBar!!.title = "Dashboard"

        startActivity(Intent(this, MainActivity::class.java))


    }
}
