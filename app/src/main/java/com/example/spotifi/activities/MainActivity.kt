package com.example.spotifi.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.spotifi.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostMain).navigateUp()
    }

//    TODO: add bottom navigator
//    private fun setupNavigation() {
//        val navController: NavController = findNavController(R.id.navHostMain)
//    }
}