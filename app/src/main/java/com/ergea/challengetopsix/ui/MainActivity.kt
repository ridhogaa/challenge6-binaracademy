package com.ergea.challengetopsix.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ergea.challengetopsix.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }
}