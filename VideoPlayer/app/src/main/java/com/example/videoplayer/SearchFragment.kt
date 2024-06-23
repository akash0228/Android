package com.example.videoplayer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.videoplayer.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var videoViewModel:VideoViewModel
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoViewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(VideoViewModel::class.java)
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
            if (parentFragment is HomeFragment )
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