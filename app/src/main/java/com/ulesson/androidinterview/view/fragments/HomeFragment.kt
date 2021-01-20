package com.ulesson.androidinterview.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.ulesson.androidinterview.R
import com.ulesson.androidinterview.model.local.entities.Lesson
import com.ulesson.androidinterview.model.local.entities.LessonAndSubject
import com.ulesson.androidinterview.model.local.entities.Subject
import com.ulesson.androidinterview.navigation.NavDispatcher
import com.ulesson.androidinterview.view.adapters.RecentlyViewedListAdapter
import com.ulesson.androidinterview.view.adapters.SubjectGridAdapter
import com.ulesson.androidinterview.viewmodel.LessonViewModel
import com.ulesson.androidinterview.viewmodel.SubjectViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), SubjectGridAdapter.OnItemClickListener,
    RecentlyViewedListAdapter.OnItemInteractionListener {

    private val viewModel: SubjectViewModel by viewModels()
    private val lessonsModel: LessonViewModel by viewModels()
    private lateinit var adapter: SubjectGridAdapter
    private lateinit var recentlyPlayedAdapter: RecentlyViewedListAdapter
    private lateinit var recentList: List<LessonAndSubject>
    private var isMore = false

    @Inject
    lateinit var navigator: Provider<NavDispatcher>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getSubjects()
        lessonsModel.getRecentlyPlayed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SubjectGridAdapter(this)
        recentlyPlayedAdapter = RecentlyViewedListAdapter(this)
        recentlyViewedList.adapter = recentlyPlayedAdapter
        recentlyViewedGroup.visibility = View.GONE
        viewMoreBtn.visibility = View.GONE
        subjectList.adapter = adapter
        viewMoreBtn.setOnClickListener {
            setRecentlyViewedList(!isMore)
            viewMoreBtn.text = if (isMore) ("See Less") else ("View All")
        }
        setObServers()
    }

    override fun onItemClick(item: Subject) {
        navigator.get().openSubject(item)
    }

    override fun onItemClick(lesson: Lesson) {
        navigator.get().openRecentlyPlayed(lesson)
    }

    private fun setRecentlyViewedList(isExpanded: Boolean = false) {
        isMore = isExpanded
        if (::recentList.isInitialized && recentList.isNotEmpty()) {
            viewMoreBtn.visibility = if (recentList.size > 2) View.VISIBLE
            else View.GONE
            val sorted = recentList.sortedByDescending { it.lesson.id }
            recentlyViewedGroup.visibility = View.VISIBLE
            val data = when {
                !isMore && sorted.size >= 2 -> sorted.subList(0, 2)
                else -> sorted
            }
            recentlyPlayedAdapter.submitList(data)

        }
    }

    private fun setObServers() {
        viewModel.subjects.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        lessonsModel.recentlyPlayed.observe(viewLifecycleOwner) {
            recentList = it
            setRecentlyViewedList()
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            Log.e("HomeFragment", "Loading = $it")
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("HomeFragment", "Error = $it")
        }
    }
}