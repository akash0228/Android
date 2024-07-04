package com.example.tvapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class PlayerFragment(val show: Show,val tab:Int,val parentInterface: MainActivity.MainInterface) : Fragment() {
    lateinit var playerView:PlayerView
    lateinit var player:ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_player, container, false)

        playerView= view.findViewById(R.id.playerView)

        playerView.setOnKeyListener { v, keyCode, event ->
            if ((event.action== KeyEvent.ACTION_DOWN)) {
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to mainRv
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //do nothing
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //send focus to recent tab
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //send focus to mainRv
                    }
                    KeyEvent.KEYCODE_BACK ->{
                        Log.d("BACK", "onKeyBack: BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }


        player = ExoPlayer.Builder(requireContext()).build()
        playerView.player = player



        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(@Player.State state: Int) {

            }
        })

        val url="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        val videoUri= Uri.parse(url)

        val mediaItem = MediaItem.fromUri(videoUri)

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}