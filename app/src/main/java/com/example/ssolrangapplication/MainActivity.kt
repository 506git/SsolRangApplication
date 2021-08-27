package com.example.ssolrangapplication

import android.content.*
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ssolrangapplication.common.model.UserModel
import com.example.ssolrangapplication.common.service.MusicService
import com.example.ssolrangapplication.common.utils.CustomFragmentManger
import com.example.ssolrangapplication.common.utils.ImageLoader
import com.example.ssolrangapplication.common.utils.SsolUtils
import com.example.ssolrangapplication.ui.MusicPlayerFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    lateinit var behavior: BottomSheetBehavior<View>
    private var mediaPlayer : MediaPlayer? = null
//    private lateinit var audioManger : AudioManager
    var playing = true

    lateinit var mService: MusicService
    private var mBound: Boolean = false
    private var time: Int = 0
    private val connection = object : ServiceConnection{
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as MusicService.LocalBinder
            mService = binder.getService()
            mBound = true
            mService.player?.let{
                mediaPlayer = it
            }
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this,MusicService::class.java),connection,Context.BIND_AUTO_CREATE)
//        Intent(this,MusicService::class.java).also {
//                intent -> bindService(intent,connection,Context.BIND_AUTO_CREATE)
//        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = intent.getSerializableExtra("userInf") as UserModel
        initViewModel()
//        mediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.sound).apply {
//            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build()
//            )
//            isLooping = true
//        }
//        audioManger = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mainViewModel.setUser(user.name, user.id, user.profile)
        val playProgress = findViewById<ProgressBar>(R.id.progress)
        playProgress.apply {
            isClickable = false

        }

        findViewById<ImageButton>(R.id.play).setOnClickListener {
            if(!mBound){
                return@setOnClickListener
            }
            playProgress.max = mediaPlayer?.duration!!
            if(mediaPlayer?.isPlaying == false) {
                Log.d("testBOolena3", "time.toString()")
                mService.musicStart()
                CoroutineScope(Main).launch {
                    while (mediaPlayer?.isPlaying == true) {
                        time = withContext(IO){
                            mediaPlayer?.currentPosition!!
                        }
                        delay(100)
                        playProgress.progress = time
                    }
                }.start()
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startService(Intent(this,MusicService::class.java).apply {
                        putExtra("notify",true)
                    })
                }
            } else {
                Log.d("testBOolena4", "time.toString()")
                mService.musicStop()
                playing = false
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    startService(Intent(this,MusicService::class.java).apply {
                        putExtra("notify",false)
                    })
                }
            }
        }

        findViewById<ImageView>(R.id.profile).setOnClickListener {
            SsolUtils.showCustomWindow(
                this,
                "알림",
                "로그아웃하시겠습니까?",
                "확인",
                false,
                { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    signOut()
                },
                { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
            )

        }
        findViewById<TextView>(R.id.title).setOnClickListener {
            mainViewModel.setState(BottomSheetBehavior.STATE_COLLAPSED)
        }

        behavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet)).apply {
            this.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        val fragment = MusicPlayerFragment()
                        CustomFragmentManger(this@MainActivity).addFragment(fragment, "musicPlayer")
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    findViewById<View>(R.id.collapseView).animate()
                        .alpha(1.0f - slideOffset * 2).duration = 0
                    findViewById<View>(R.id.fragment_view).animate()
                        .alpha(slideOffset * 2).duration = 0
                }
            })
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

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

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.title.observe(this, Observer {
            findViewById<TextView>(R.id.title).text = it
        })
        mainViewModel.user.observe(this, Observer {
            ImageLoader(this).imageCircleLoadWithURL(
                it.profile,
                findViewById<ImageView>(R.id.profile)
            )
        })
        mainViewModel.bottomState.observe(this, Observer {
            behavior.state = it
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            if(mainViewModel.bottomState.equals(BottomSheetBehavior.STATE_EXPANDED)){
                mainViewModel.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
            return false
        }
        return true
    }

    private fun signOut() {
        val googleSignInClient = GoogleSignIn.getClient(
            this, GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
            ).build()
        )
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