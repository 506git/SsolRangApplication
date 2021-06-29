package com.example.ssolrangapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ssolrangapplication.common.model.UserModel
import com.example.ssolrangapplication.common.utils.ImageLoader
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = intent.getSerializableExtra("userInf") as UserModel
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.title.observe(this, Observer {
            findViewById<TextView>(R.id.title).text = it
        })
        mainViewModel.setUser(user.name,user.id,user.profile)

        mainViewModel.user.observe(this, Observer {
            ImageLoader(this).imageCircleLoadWithURL(
                it.profile,
                findViewById<ImageView>(R.id.profile)
            )
        })

        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.icon_search)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            mainViewModel.titleChange(when (destination.id) {
                R.id.navigation_home -> getString(R.string.title_home)
                R.id.navigation_dashboard -> getString(R.string.title_dashboard)
                R.id.navigation_notifications -> getString(R.string.title_notifications)
                else -> getString(R.string.title_home)
            })
        }
    }
}