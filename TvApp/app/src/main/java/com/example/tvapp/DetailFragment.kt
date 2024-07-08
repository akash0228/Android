package com.example.tvapp

import android.icu.text.Transliterator.Position
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tvapp.databinding.FragmentDetailBinding

class DetailFragment(val show: Show,val parentInterface: MainActivity.MainInterface,var tab:Int,val rowPosition:Int,val childPos:Int) : Fragment() {
     private lateinit var watchCardView: CardView
     private lateinit var laterCardView: CardView
    lateinit var showRowViewModel: ShowRowViewModel
    lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun restorefocus(){
        watchCardView.requestFocus()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showRowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ShowRowViewModel::class.java]
        // Inflate the layout for this fragment

        binding=FragmentDetailBinding.inflate(inflater, container, false)



        watchCardView=binding.root.findViewById(R.id.watchCard)
        laterCardView=binding.root.findViewById(R.id.laterCard)

        watchCardView.requestFocus()

        if (show.inWatchList){
            binding.latertv.text="UnWatchlist"
            changeColor(laterCardView)
        }

        watchCardView.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper

                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down

                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //parent request left

                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right
                        laterCardView.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //open detail page
                        changeColor(watchCardView)
                        parentInterface.onKeyCenterPlay(show,tab,rowPosition,childPos)
                    }
                    KeyEvent.KEYCODE_BACK->{
                        Log.d("BACK", " BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }

        laterCardView.setOnKeyListener { v, keyCode, event ->
            if (event.action== KeyEvent.ACTION_DOWN){
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //parent request upper

                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //parent request down

                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //parent request left
                        watchCardView.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //parent request right

                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //add update
                        if (!show.inWatchList) {
                            show.inWatchList=true
                            showRowViewModel.update(show)
                            changeColor(laterCardView)
                        }
                        else{
                            show.inWatchList=false
                            showRowViewModel.update(show)
                            binding.latertv.text="Watch Later"
                            unchangeColor(laterCardView)

                        }
                    }
                    KeyEvent.KEYCODE_BACK->{
                        Log.d("BACK", " BACK")
                        parentInterface.OnKeyBack(tab)
                    }
                }
            }
            true
        }

        binding.detailPosterTitle.text=show.title
        binding.detailPosterCategory.text="${show.category} * ${show.year} * ${show.Duration}"
        binding.detailPosterDescription.text=show.Description
        val url=show.imageUrl
        Glide.with(requireContext())
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.mainPoster);

        return binding.root
    }

    fun changeColor(view: CardView){
        view.setCardBackgroundColor(resources.getColor(R.color.pressed_color))
    }

    fun unchangeColor(view: CardView){
        view.setCardBackgroundColor(resources.getColor(R.color.focused_color_show))
    }

}