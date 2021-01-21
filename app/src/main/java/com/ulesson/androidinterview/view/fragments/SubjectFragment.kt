package com.ulesson.androidinterview.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.navigation.NavDispatcher
import com.ulesson.androidinterview.utils.decorators.SubjectItemDecorator
import com.ulesson.androidinterview.view.adapters.ChaptersListAdapter
import com.ulesson.androidinterview.view.adapters.LessonListAdapter
import com.ulesson.androidinterview.viewmodel.ChapterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_subject.*
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SubjectFragment : Fragment(), LessonListAdapter.OnItemClickListener {

    private val args by navArgs<SubjectFragmentArgs>()

    @Inject
    lateinit var navigator: Provider<NavDispatcher>

    private val viewModel: ChapterViewModel by viewModels()
    private lateinit var adapter: ChaptersListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getChapters(args.subject.id)
        adapter = ChaptersListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onItemClick(item: Lesson) {
        navigator.get().openLesson(item, "")
    }

    private fun setUpViews() {
        backButton.setOnClickListener {
            navigator.get().navigateUp()
        }
        setObservers()
        val subject = args.subject
        with(subject) {
            title.text = name
            adapter.setLessonListener(this@SubjectFragment)
            chaptersList.addItemDecoration(SubjectItemDecorator())
            chaptersList.adapter = adapter
        }
    }

    private fun setObservers() {
        viewModel.chapters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}