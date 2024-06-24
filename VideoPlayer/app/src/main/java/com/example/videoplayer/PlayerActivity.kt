package com.example.videoplayer

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.ComponentName
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Rect
import androidx.media3.session.MediaSession
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class PlayerActivity : AppCompatActivity()  {
    private lateinit var playerView: PlayerView
    private lateinit var mediaItem: MediaItem
    private lateinit var mediaControllerFuture:ListenableFuture<MediaController>
    private var isPipMode=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val title:String=intent.getStringExtra("videoTitle").toString()
        val url:String=intent.getStringExtra("videoURL").toString()
        val lyrics:String=intent.getStringExtra("videoLyrics").toString()

        val videoTitle:TextView=findViewById(R.id.videoTitle)
        val videoLyrics:TextView=findViewById(R.id.videoLyrics)

        videoTitle.text=title
        videoLyrics.text=lyrics



        playerView=findViewById(R.id.playerView)
        playerView.resizeMode=AspectRatioFrameLayout.RESIZE_MODE_FIT


        val videoUri= Uri.parse(url)

        mediaItem=MediaItem.Builder().setUri(videoUri).setMediaMetadata(
            MediaMetadata.Builder()
                .setArtist("Arijit Singh")
                .setTitle(title)
                .build()
        ).build()

    }

    override fun onStart() {

        val sessionToken= SessionToken(this, ComponentName(this,PlaybackService::class.java))
        mediaControllerFuture=MediaController.Builder(this,sessionToken).buildAsync()
        mediaControllerFuture.addListener(
            {
                val mediaController:MediaController=mediaControllerFuture.get()
                playerView.player=mediaController
                mediaController.setMediaItem(mediaItem)
                mediaController.prepare()
                mediaController.playWhenReady
            },MoreExecutors.directExecutor()
        )
        super.onStart()
    }

    override fun onBackPressed() {
        if (!isPipMode) {
            createPIPMode()
            isPipMode = true
        } else {
            super.onBackPressed()
        }
//        startActivity(Intent(this,MainActivity::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        if (newConfig!=null){
            isPipMode = !isInPictureInPictureMode
        }
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        createPIPMode()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createPIPMode() {
        startPictureInPictureWithRatio(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startPictureInPictureWithRatio(activity: Activity) {
        activity.enterPictureInPictureMode(
            PictureInPictureParams.Builder()
                .setAspectRatio(Rational(16, 9))
                .build()
        )
    }

    override fun onStop() {
        super.onStop()
        MediaController.releaseFuture(mediaControllerFuture)
    }

    override fun onDestroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        super.onDestroy()
    }

}