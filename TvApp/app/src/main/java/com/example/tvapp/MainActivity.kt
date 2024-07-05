package com.example.tvapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){
    var flag=0
    var initialFlag=false

    lateinit var allTab:CardView
    lateinit var recentTab:CardView
    lateinit var watchlist:CardView
    lateinit var searchTab:CardView
    lateinit var allFragment:AllFragment
    lateinit var recentFragment:RecentFragment
    lateinit var watchListFragment:WatchListFragment
    lateinit var searchFragment:SearchFragment
    lateinit var detailFragment:DetailFragment
    lateinit var showRowViewModel:ShowRowViewModel

    val mainInterface =object : MainInterface{
        override fun onKeyUp(tab: Int) {
            when(tab){
                0 ->{
                    allTab.requestFocus()
                }
                1->{
                    recentTab.requestFocus()
                }
                2->{
                    watchlist.requestFocus()
                }
                3->{
                    searchTab.requestFocus()
                }
            }
        }

        override fun onKeyCenter(show: Show,tab: Int) {
            loadDetailFrag(show,tab)
        }

        override fun OnKeyBack(tab: Int) {
            Log.d("MAin BACK", " BACK")
            loadFrag(null,3)

            when(tab){
                0 -> allFragment.restoreFocus()
                1->recentFragment.restoreFocus()
                2->watchListFragment.restoreFocus()
                3->searchFragment.restoreFocus()

            }
        }

        override fun onKeyCenterPlay(show: Show,tab: Int) {
            loadPlayerFrag(show,tab)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allTab=findViewById(R.id.all)
        recentTab=findViewById(R.id.recent)
        watchlist=findViewById(R.id.watchlist)
        searchTab=findViewById(R.id.searchTab)
        val container:FrameLayout=findViewById(R.id.container)
        allFragment=AllFragment(mainInterface)
        recentFragment=RecentFragment(mainInterface)
        watchListFragment=WatchListFragment(mainInterface)
        searchFragment=SearchFragment(mainInterface)

        showRowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[ShowRowViewModel::class.java]
        //insertInitialData
        if (initialFlag)
        insertInitialdata()

        //insert data

        val searchView:SearchView=findViewById(R.id.searchView)

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            }
            else {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                searchView.setQuery("", false)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search/filter action
                loadFrag(searchFragment,flag)
                //handle here focus
                searchTab.requestFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter data based on newText
                return true
            }
        })

        searchView.setOnKeyListener { v, keyCode, event ->
            if ((event.action==KeyEvent.ACTION_DOWN)) {
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //if search result then only
                        searchFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //do nothing
                        searchTab.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //do nothing

                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //if search result then only
                        searchFragment.focus(0)
                    }
                }
            }
            true
        }

        allTab.requestFocus()
        loadFrag(allFragment,flag)
        flag=1

//        loadFrag(PlayerFragment(),0)

        //on Focus changes tracker
        allTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                loadFrag(allFragment,flag)
            }
        }

        recentTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                loadFrag(recentFragment,flag)
            }
        }

        watchlist.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                loadFrag(watchListFragment,flag)
            }
        }

        searchTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                loadFrag(searchFragment,flag)
            }
        }


        allTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if ((event.action==KeyEvent.ACTION_DOWN)) {
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to mainRv
                        allFragment.focus(0,0)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //do nothing
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //send focus to recent tab
                        recentTab.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //send focus to mainRv

                    }
                }
            }
            true
        })

        recentTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN){
                Log.d("TAB", "onCreate: KEY Pressed")
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to GridV
                        recentFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //send focus to all tab
                        allTab.requestFocus()

                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        watchlist.requestFocus()

                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //send focus to grid
                        recentFragment.focus(0)
                    }
                }
            }
            true
        })

        watchlist.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN){
                Log.d("TAB", "onCreate: KEY Pressed")

                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to GridV
                        watchListFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //send focus to recent tab
                        recentTab.requestFocus()

                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //send focus to searchView tab
                        searchTab.requestFocus()
//                    loadFrag(SearchFragment(),0)
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //send focus to grid
                        watchListFragment.focus(0)

                    }
                }
            }
            true
        })

        searchTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN){
                Log.d("TAB", "onCreate: KEY Pressed")
                Log.d("TAB", "onCreate: ${currentFocus}")
                when(keyCode){
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to GridV
                        searchFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //send focus to watchlist tab
                        watchlist.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //do nothing
                        searchView.requestFocus()
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER ->{
                        //search then send focus to gridV
//                        searchFragment.focus(0)
                    }
                }
            }
            true
        })

    }

    fun loadFrag(frag: Fragment?, flag:Int){
        val fm: FragmentManager=supportFragmentManager
        val ft=fm.beginTransaction()

        if(flag==0){
            frag?.let { ft.add(R.id.container, it) }
            ft.addToBackStack(null)
        }
        else if (flag==1){
            if (frag != null) {
                ft.replace(R.id.container,frag)
            }
        }
        else if(flag==2){
            if (frag != null) {
                ft.replace(R.id.container,frag).addToBackStack(null)
            }
        }
        else{
            //pop out last one
            Log.d("BACK", "loadFrag: BACK")
            fm.popBackStack()
        }
        ft.commit()
    }

    fun loadDetailFrag(show: Show,tab: Int){
        detailFragment=DetailFragment(show,mainInterface,tab)
        loadFrag(detailFragment, 0)
    }

    fun loadPlayerFrag(show: Show,tab: Int){
        loadFrag(PlayerFragment(show,tab,mainInterface),2)
    }

    fun insertInitialdata(){
        //first row
        val listShow1= listOf(Show("Movie 1","This is Movie 1 Description","Romance",2020,"1h 30m"),Show("Movie 2","This is Movie 2 Description","Romance",2021,"1h 35m"),Show("Movie 3","This is Movie 3 Description","Action",2005,"1h 40m"),Show("Movie 4","This is Movie 4 Description","Thriller",2023,"2h 30m"),Show("Movie 5","This is Movie 5 Description","Horror",2018,"1h 23m"))
        //second row
        val listShow2= listOf(Show("Movie 1","This is Movie 1 Description","Romance",2020,"1h 30m"),Show("Movie 2","This is Movie 2 Description","Romance",2021,"1h 35m"),Show("Movie 3","This is Movie 3 Description","Action",2005,"1h 40m"),Show("Movie 4","This is Movie 4 Description","Thriller",2023,"2h 30m"),Show("Movie 5","This is Movie 5 Description","Horror",2018,"1h 23m"))

        val showRow1=ShowRow("Top Picks",listShow1)
        val showRow2=ShowRow("Trending",listShow2)

        showRowViewModel.insert(showRow1)
        showRowViewModel.insert(showRow2)
    }

    interface MainInterface{
        fun onKeyUp(tab:Int)
        fun onKeyCenter(show: Show,tab: Int)

        fun OnKeyBack(tab: Int)

        fun onKeyCenterPlay(show: Show,tab: Int)
    }


}