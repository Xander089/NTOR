package com.example.ntor.presentation.run

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ntor.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, RunActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run)

    }
}