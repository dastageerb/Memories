package com.example.memories.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.memories.R
import com.example.memories.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{

    private lateinit var navController: NavController;



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)


        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment , R.id.logInFragment , R.id.registerFragment , R.id.verifyOtpFragment
            ))


        setupActionBarWithNavController(navController,appBarConfiguration)


      //  binding..setupWithNavController(navController)
//
//
//        binding.activityMainBottomNavigationView.setOnItemReselectedListener ()
//        {}
//
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
////            if(destination.id == R.id.resultFragment)
////            {
////                binding.activityMainBottomNavigation.hide()
////            }
////            else if(destination.id == R.id.premiumFragment)
////            {
////                binding.activityMainBottomNavigation.hide()
////            }
////            else if(destination.id == R.id.cropFragment)
////            {
////                binding.activityMainBottomNavigation.hide()
////            }
////            else
////            {
////                lifecycleScope.launch(Dispatchers.Main)
////                {
////                    // delay(100)
////                    binding.activityMainBottomNavigation.show()
////                }
////            }
//        }

    } // onCreate




    override fun onSupportNavigateUp(): Boolean
    {
        return navController.navigateUp() || super.onSupportNavigateUp()
    } // onSupportNavigateUp closed




} // MainActivity closed