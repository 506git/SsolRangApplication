package com.example.ssolrangapplication

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ssolrangapplication.common.model.UserModel
import com.example.ssolrangapplication.common.utils.ImageLoader
import com.example.ssolrangapplication.common.utils.SsolUtils
import com.example.ssolrangapplication.ui.player.ItemListDialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
        mainViewModel.setUser(user.name, user.id, user.profile)

        mainViewModel.user.observe(this, Observer {
            ImageLoader(this).imageCircleLoadWithURL(
                it.profile,
                findViewById<ImageView>(R.id.profile)
            )
        })
        findViewById<ImageView>(R.id.profile).setOnClickListener {
            SsolUtils.showCustomWindow(this,"알림","로그아웃하시겠습니까?","확인",false, { dialogInterface, _ ->
                dialogInterface.dismiss()
                signOut()
            }, { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            )

        }
        findViewById<TextView>(R.id.title).setOnClickListener {
            val bottom = ItemListDialogFragment()
            bottom.show(this.supportFragmentManager,"")
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            mainViewModel.titleChange(
                when (destination.id) {
                    R.id.navigation_home -> getString(R.string.title_home)
                    R.id.navigation_dashboard -> getString(R.string.title_dashboard)
                    R.id.navigation_notifications -> getString(R.string.title_notifications)
                    else -> getString(R.string.title_home)
                }
            )
            openAppBar()
        }
    }

    private fun signOut() {
        val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
        googleSignInClient.signOut()
            .addOnCompleteListener(this, OnCompleteListener<Void?> {
                FirebaseAuth.getInstance().signOut()
                this.finish()
            })
    }

    private fun openAppBar(){
        findViewById<AppBarLayout>(R.id.app_bar_layout).setExpanded(true, false)
        findViewById<AppBarLayout>(R.id.app_bar_layout).stopNestedScroll()
    }
}