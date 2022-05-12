package com.example.spotifi.fragments

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.data.remote.models.MusicApi
import com.example.data.remote.providers.implementations.MusicProviderImpl
import com.example.data.remote.providers.interfaces.MusicProvider
import com.example.domain.repositories.implementations.MusicRepositoryImpl
import com.example.domain.repositories.interfaces.MusicRepository
import com.example.domain.view_models.MusicPlayerViewModel
import com.example.domain.view_models.MusicPlayerViewModelFactory
import com.example.spotifi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_music_player.*

class MusicPlayerFragment : Fragment() {

    private lateinit var viewModel: MusicPlayerViewModel
    private lateinit var viewModelFactory: MusicPlayerViewModelFactory
    private lateinit var repository: MusicRepository
    private lateinit var provider: MusicProvider
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var musicIds: List<Int>
    private var selectedMusicIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel(view.context)
        setupMediaPlayerProgressListener()
        setupButtons()

        val musicId = arguments?.getInt("id")!!
        musicIds = arguments?.getIntegerArrayList("ids")!!
        selectedMusicIndex = musicIds.indexOf(musicId)


        viewModel.getMusicById(musicId)

        viewModel.selectedMusic.observe(viewLifecycleOwner) { music ->
            resetTimeOfSeekbar()
            setupMusic(music, view.context)
            setupMediaPlayer(music.url)
            initSeekbarLine()
            updateStateOfButtons()
        }

        viewModel.likedMusic.observe(viewLifecycleOwner) {
            makeItLiked(view.context)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, "$errorMessage", Toast.LENGTH_SHORT)
            findNavController().popBackStack()
        }



        seekbar_line.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress < 1) {
                    seekbar_line.progress = 1
                }
                if (fromUser) {
                    mediaPlayer.seekTo(progress * 1000)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        back_to_list.setOnClickListener {
            pauseMusicPlayer()
            findNavController().popBackStack()
        }
    }

    private fun resetTimeOfSeekbar() {
        pass.text = "00:00"
        due.text = "00:00"
    }

    private fun setupButtons() {
        play_button.setOnClickListener {
            startMusicPlayer()
        }

        pause_button.setOnClickListener {
            pauseMusicPlayer()
        }

        skip_next_button.setOnClickListener {
            if (haveNextMusic()) {
                pauseMusicPlayer()
                nextMusic()
            }
        }

        skip_prev_button.setOnClickListener {
            if (havePrevMusic()) {
                pauseMusicPlayer()
                prevMusic()
            }
        }

        like_button.setOnClickListener {
            if (!isLikedMusic()) {
                viewModel.likeMusic(musicIds[selectedMusicIndex])
            }
        }
    }

    private fun makeItLiked(context: Context) {
        like_button.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_red
            )
        )
    }

    private fun makeItNoLiked(context: Context) {
        like_button.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite
            )
        )
    }

    private fun isLikedMusic(): Boolean {
        return like_button.background.toString() == R.drawable.ic_favorite.toString()
    }

    private fun nextMusic() {
        selectedMusicIndex += 1
        viewModel.getMusicById(musicIds[selectedMusicIndex])
    }

    private fun prevMusic() {
        selectedMusicIndex -= 1
        viewModel.getMusicById(musicIds[selectedMusicIndex])
    }

    private fun haveNextMusic(): Boolean {
        return selectedMusicIndex + 1 < musicIds.size
    }

    private fun havePrevMusic(): Boolean {
        return selectedMusicIndex - 1 > 0
    }

    private fun updateStateOfButtons() {
        if (!haveNextMusic()) {
            skip_next_button.visibility = View.INVISIBLE
        } else {
            skip_next_button.visibility = View.VISIBLE
        }

        if (!havePrevMusic()) {
            skip_prev_button.visibility = View.INVISIBLE
        } else {
            skip_prev_button.visibility = View.VISIBLE
        }
    }

    private fun setupMusic(music: MusicApi, context: Context) {
        Picasso.get().load(music.preview_url).into(preview_image)
        title.text = music.title
        artist.text = music.artist.title
        if (music.is_liked) {
            makeItLiked(context)
        } else {
            makeItNoLiked(context)
        }
    }

    private fun initSeekbarLine() {
        seekbar_line.max = mediaPlayer.duration / 1000
    }

    private fun updateSeekbarProgress() {
        if (mediaPlayer != null && seekbar_line != null) {
            seekbar_line.progress = mediaPlayer.currentPosition / 1000
            pass!!.text = String.format(
                "%02d:%02d",
                seekbar_line.progress / 60,
                seekbar_line.progress % 60
            )
            val difference =
                mediaPlayer.duration / 1000 - mediaPlayer.currentPosition / 1000
            due!!.text = String.format("%02d:%02d", difference / 60, difference % 60)
        }
    }


    private fun setupViewModel(context: Context) {
        provider = MusicProviderImpl(context)
        repository = MusicRepositoryImpl(provider)
        viewModelFactory = MusicPlayerViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MusicPlayerViewModel::class.java]
    }

    private fun setupMediaPlayerProgressListener() {
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                Log.d("Handler for seekbar:", "I am running bro...")
                updateSeekbarProgress()
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun startListenMediaPlayerProgress() {
        handler.postDelayed(runnable!!, 0)
    }

    private fun stopListenMediaPlayerProgress() {
        Log.d("Handler ", "Trying off")
        handler.removeMessages(0)
    }

    private fun setupMediaPlayer(url: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()

        mediaPlayer.setOnCompletionListener {
            showHidePlayPauseButtons()
            stopListenMediaPlayerProgress()
        }
    }

    private fun pauseMusicPlayer() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()

            showHidePlayPauseButtons()
            stopListenMediaPlayerProgress()
        } else {
            Toast.makeText(context, "Some error...", Toast.LENGTH_SHORT)
        }
    }

    private fun startMusicPlayer() {
        mediaPlayer.start()

        showHidePlayPauseButtons()
        startListenMediaPlayerProgress()
    }

    private fun showHidePlayPauseButtons() {
        if (play_button.visibility == View.GONE) {
            play_button.visibility = View.VISIBLE
            pause_button.visibility = View.GONE
        } else {
            play_button.visibility = View.GONE
            pause_button.visibility = View.VISIBLE
        }
    }


}


