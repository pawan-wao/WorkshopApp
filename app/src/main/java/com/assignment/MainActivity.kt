package com.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.assignment.databinding.ActivityMainBinding
import com.assignment.databinding.FragmentLoginBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//        binding.bottomNavView.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == R.id.fragmentLogin || destination.id == R.id.fragmentSignup) {
//                binding.bottomNavView.visibility = View.GONE
//            } else {
//                binding.bottomNavView.visibility = View.VISIBLE
//            }
//        }

    }
}