package com.example.ssolrangapplication.ui.player

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.example.ssolrangapplication.MainActivity
import com.example.ssolrangapplication.R

class NotificationPlayer {

//    suspend fun notifySetting(){
//        val mMainIntent = Intent(this, MainActivity::class.java)
//        val mPendingIntent =
//            PendingIntent.getActivity(this, 1, mMainIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        val mBuilder = NotificationCompat.Builder(this,channelId)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("음악 테스트")
//            .setContentIntent(mPendingIntent)
//            .setContentText("백그라운드 음악이 재생중!")
//            .addAction(
//                R.drawable.icon_search,"previous",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS))
//            .addAction(
//                R.drawable.icon_search,"next",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_NEXT))
//            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,2,1))
////                .setContentIntent(mPendingIntent)
//            .setAutoCancel(true)
//        if (player?.isPlaying == true){
//            mBuilder.addAction(
//                R.drawable.icon_search,"playPause",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY_PAUSE))
//        } else{
//            mBuilder.addAction(
//                R.drawable.icon_search,"playStart",
//                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_PLAY))
//        }
////            val mNotify: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//        notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
//    }
}