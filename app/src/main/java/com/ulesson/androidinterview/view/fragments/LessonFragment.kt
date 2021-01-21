package com.ulesson.androidinterview.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.viewmodel.ChapterViewModel
import com.ulesson.androidinterview.viewmodel.LessonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_lesson.*

@AndroidEntryPoint
class LessonFragment : Fragment() {

    private val args by navArgs<LessonFragmentArgs>()

    private val viewModel: LessonViewModel by viewModels()
    private val chapterViewModel: ChapterViewModel by viewModels()
    private lateinit var player: SimpleExoPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chapterViewModel.getChapterNameById(args.lesson.chapter_id)
        setUpViews()
    }

    private fun setUpViews() {
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        val lesson = args.lesson
        with(lesson) {
            createPlayer(media_url)
            //playerView.con
            lessonName.text = this.name
        }
        chapterViewModel.chapterName.observe(viewLifecycleOwner) {
            chapterName.text = it
        }
    }

    override fun onStop() {
        super.onStop()
        if (::player.isInitialized) {
            if (player.isPlaying)
                player.stop()
            player.release()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::player.isInitialized && player.isPlaying)
            player.pause()
    }

    override fun onResume() {
        super.onResume()
        if (::player.isInitialized)
            player.play()
    }

    private fun createPlayer(uri: String) {
        if (!::player.isInitialized) {
            player = SimpleExoPlayer.Builder(requireContext()).build()
            playerView.player = player
        } else {
            if (player.isPlaying) {
                player.stop()
                player.release()
            }
        }
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        val media = MediaItem.fromUri(uri)
        player.setMediaItem(media)
        player.prepare()
        player.play()
        viewModel.setAsRecentlyPlayed(args.lesson)
    }

}