package com.example.videoplayer

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoplayer.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private lateinit var binding:FragmentFavouriteBinding
    private lateinit var listVideo: List<Video>
    private lateinit var videoViewModel:VideoViewModel
    private lateinit var adapter: RecyclerVideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoViewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(VideoViewModel::class.java)
        adapter=RecyclerVideoAdapter(requireContext(), mutableListOf(),videoViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavouriteBinding.inflate(inflater,container,false)

        binding.rv.layoutManager= LinearLayoutManager(requireContext())
        binding.rv.setHasFixedSize(true)

        videoViewModel.getFavVideos().observe(viewLifecycleOwner, Observer { videos ->
            listVideo = videos
            adapter = RecyclerVideoAdapter(requireContext(), listVideo,videoViewModel)
            binding.rv.adapter = adapter
        })

        binding.rv.adapter=adapter

        val itemTouchHelper= ItemTouchHelper(
            object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction== ItemTouchHelper.RIGHT){
                        Toast.makeText(requireContext(),"Video Deleted", Toast.LENGTH_SHORT).show()
                        videoViewModel.delete(listVideo.get(viewHolder.adapterPosition))
                        adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    }
                    else{
                        try{
                            val dialog= Dialog(requireContext())
                            dialog.setContentView(R.layout.add_update_lay)

                            val txtTitle: TextView =dialog.findViewById(R.id.txtTitle)
                            txtTitle.setText("Update Video")

                            val editTitle: EditText =dialog.findViewById(R.id.editTitle)
                            val editURL: EditText =dialog.findViewById(R.id.editURL)
                            val editLyrics: EditText =dialog.findViewById(R.id.editLyrics)
                            val btnAction: Button =dialog.findViewById(R.id.btnAction)

                            val currVideo=listVideo.get(viewHolder.adapterPosition)
                            editTitle.setText(currVideo.title)
                            editURL.setText(currVideo.url)
                            editLyrics.setText(currVideo.lyrics)
                            btnAction.setText("Update")


                            dialog.show()


                            btnAction.setOnClickListener{
                                currVideo.url=editURL.text.toString()
                                currVideo.lyrics=editLyrics.text.toString()
                                currVideo.title=editTitle.text.toString()

                                videoViewModel.update(currVideo)

                                adapter.notifyItemChanged(viewHolder.adapterPosition)
                                binding.rv.scrollToPosition(viewHolder.adapterPosition)

                                dialog.dismiss()
                            }
                        }
                        catch (e:Exception){
                            Log.d("TAG", "onCreateView:${e.message}")
                        }


                    }
                }

            }
        ).attachToRecyclerView(binding.rv)


        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteFragment()

    }
}