package com.example.jokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.text.HtmlCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.google.android.material.navigation.NavigationView
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        setContentView( R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        var navView : NavigationView = findViewById(R.id.navView)
        //or use binding!

        Timber.i("MainActivity is created")


        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(navView, navController)

        val titleString = "<font color=\"black\">" + getString(R.string.app_name) + "</font>"
        supportActionBar?.setTitle(HtmlCompat.fromHtml(titleString, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}