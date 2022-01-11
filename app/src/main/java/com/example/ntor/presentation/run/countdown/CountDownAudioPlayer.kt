package com.example.ntor.presentation.run.countdown

import android.content.Context
import android.media.MediaPlayer

class CountDownAudioPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun create(context: Context, resId: Int) {
        mediaPlayer = MediaPlayer.create(context, resId)
    }

    private fun notCreated() = mediaPlayer == null

    fun start(context: Context, resId: Int) {
        if (notCreated()) {
            create(context, resId)
        }
        mediaPlayer?.start()
    }

    fun release(){
        mediaPlayer?.release()
        mediaPlayer = null
    }

}