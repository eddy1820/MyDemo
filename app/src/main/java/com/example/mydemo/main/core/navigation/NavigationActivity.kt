package com.example.mydemo.main.core.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.mydemo.R
import com.example.mydemo.databinding.ActivityNavigetionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNavigetionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
            //it needs actionBar
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
//        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val controller = Navigation.findNavController(this, R.id.fragment)
//        return controller.navigateUp()
////        return super.onSupportNavigateUp()
//    }
}