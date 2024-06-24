package com.example.videoplayer.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.videoplayer.databinding.FragmentSearchBinding
import com.example.videoplayer.ui.viewmodels.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val videoViewModel: VideoViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parentFragment=requireParentFragment()
        binding=FragmentSearchBinding.inflate(inflater,container,false)

        binding.searchIcon.setOnClickListener{
            Log.d("TAG", "searchIcon: Clicked")
            if (parentFragment is HomeFragment)
              parentFragment.filterVideos(binding.searchText.text.toString())
            if (parentFragment is FavouriteFragment)
                parentFragment.filterVideos(binding.searchText.text.toString())

        }

        binding.searchText.addTextChangedListener{query ->
            if (parentFragment is HomeFragment)
             parentFragment.filterVideos(query.toString())
            if (parentFragment is FavouriteFragment)
                parentFragment.filterVideos(query.toString())
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}