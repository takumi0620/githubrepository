package com.example.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpView()
    }

    private fun setUpView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.top, TopFragment.newInstance() as Fragment)
        transaction.commitNow()
    }
}
