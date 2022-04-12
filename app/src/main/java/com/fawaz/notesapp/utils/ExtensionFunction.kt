package com.fawaz.notesapp.utils

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.fawaz.notesapp.MainActivity
import com.fawaz.notesapp.R
import com.google.android.material.appbar.MaterialToolbar

object ExtensionFunction {
    fun MaterialToolbar.setActionBar(requireActivity: FragmentActivity) {
        val navController = findNavController()
        val appBarConfig = AppBarConfiguration(navController.graph)
        (requireActivity as MainActivity).setSupportActionBar(this)
        setupWithNavController(navController, appBarConfig)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id) {
                R.id.detailFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
                R.id.addFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
                R.id.updateFragment -> setNavigationIcon(R.drawable.ic_left_arrow)
            }
        }
    }
}