package com.example.praticetest.presenter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.praticetest.R
import com.example.praticetest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val controller by lazy {
        findNavController(R.id.activity_main_navhost)
    }


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activityMainBottomNavigation.setupWithNavController(controller)
    }
}

