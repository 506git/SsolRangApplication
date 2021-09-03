package com.example.ssolrangapplication.common.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.example.ssolrangapplication.MainActivity
import com.example.ssolrangapplication.R
import kotlinx.android.synthetic.main.bottom_sheet_player.view.*

class MusicService : MediaBrowserServiceCompat() {

    var player: MediaPlayer? = null
    var mSession: MediaSessionCompat? = null
    private val binder = LocalBinder()
    val NOTIFICATION_ID = 1001
    lateinit var notificationManager: NotificationManager
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notify: Boolean? = intent?.extras?.getBoolean("notify")
        val channelId = "$packageName-${getString(R.string.app_name)}"
      
        if(notify!!) {

            val mMainIntent = Intent(this, MainActivity::class.java)
            val mPendingIntent =
                PendingIntent.getActivity(this, 1, mMainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val mBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("음악 테스트")
                .setContentIntent(mPendingIntent)
                .setContentText("백그라운드 음악이 재생중!")
                .addAction(
                    R.drawable.icon_search,
                    "previous",
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
                    )
                )
                .addAction(
                    R.drawable.icon_search,
                    "next",
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this,
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    )
                )
                .setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(
                        0,
                        2,
                        1
                    ).setShowCancelButton(true)
                )
//                .setContentIntent(mPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                if (player?.isPlaying == true){
                    mBuilder.addAction(
                        R.drawable.icon_search,
                        "playPause",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            this,
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                        )
                    )
                } else{
                    mBuilder.addAction(
                        R.drawable.icon_search,
                        "playStart",
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            this,
                            PlaybackStateCompat.ACTION_PLAY
                        )
                    )
                }
//            val mNotify: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_LOW
                )
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
        } else {
            if (notificationManager != null){
                notificationManager.cancel(NOTIFICATION_ID)
            }
        }

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        if (player == null) {
            player = MediaPlayer.create(applicationContext, R.raw.sound)
        }

        mSession = MediaSessionCompat(this, "MusicService")
        mSession!!.setFlags(
            MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                    MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS or
                    MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        )
        sessionToken = mSession?.sessionToken
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    fun musicTimeSet(time: Int) {
        player?.let {
            it.pause()
            it.seekTo(time)
            it.start()
        }
    }

    fun musicStop() {
        player?.pause()
    }


    fun musicStart() {
        player?.start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot("", null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {

    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
}