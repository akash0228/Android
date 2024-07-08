package com.example.tvapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    var flag = 0
    var initialFlag = false
    var selectedTab=0
    var lastBackPressTime: Long = 0

    lateinit var allTab: CardView
    lateinit var recentTab: CardView
    lateinit var watchlist: CardView
    lateinit var searchTab: CardView
    lateinit var allFragment: AllFragment
    lateinit var recentFragment: RecentFragment
    lateinit var watchListFragment: WatchListFragment
    lateinit var searchFragment: SearchFragment
    lateinit var detailFragment: DetailFragment
    lateinit var showRowViewModel: ShowRowViewModel
    lateinit var showRowList:List<ShowRow>
    lateinit var tabs:ConstraintLayout
    lateinit var container:FrameLayout

    val mainInterface = object : MainInterface {
        override fun onKeyUp(tab: Int) {
            when (tab) {
                0 -> {
                    allTab.requestFocus()
                }

                1 -> {
                    recentTab.requestFocus()
                }

                2 -> {
                    watchlist.requestFocus()
                }

                3 -> {
                    searchTab.requestFocus()
                }
            }
        }

        override fun onKeyCenter(show: Show, tab: Int, rowPosition: Int, childPos: Int) {
            loadDetailFrag(show, tab, rowPosition, childPos)
            tabs.visibility=View.GONE
        }

        override fun OnKeyBack(tab: Int) {
            Log.d("MAin BACK", " BACK")
            loadFrag(null, 3)
            tabs.visibility=View.VISIBLE
            when (tab) {
                0 -> allFragment.restoreFocus()
                1 -> recentFragment.restoreFocus()
                2 -> watchListFragment.restoreFocus()
                3 -> searchFragment.restoreFocus()

            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onKeyCenterPlay(show: Show, tab: Int, rowPosition: Int, childPos: Int) {

            loadPlayerFrag(show, tab)

            if (!show.isWatched) {
                show.isWatched=true
                addToRecent(show)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allTab = findViewById(R.id.all)
        tabs = findViewById(R.id.tabs)
        recentTab = findViewById(R.id.recent)
        watchlist = findViewById(R.id.watchlist)
        searchTab = findViewById(R.id.searchTab)
        container = findViewById(R.id.container)
        allFragment = AllFragment(mainInterface)
        recentFragment = RecentFragment(mainInterface)
        watchListFragment = WatchListFragment(mainInterface)
        searchFragment = SearchFragment(mainInterface)

        showRowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)
        )[ShowRowViewModel::class.java]

        showRowViewModel.getAllShowRow().observe(this) { showRows ->
            showRowList = showRows
        }

        //insertInitialData
        if (initialFlag) {
            insertInitialdata()
        }


        val searchView: SearchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
            } else {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                searchView.setQuery("", false)
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search/filter action

                if (query != null) {
                   searchFragment.filter(query)
                }
                searchTab.requestFocus()
                //handle here focus
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter data based on newText
                return true
            }
        })

        searchView.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN)) {
                when (keyCode) {
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

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        //if search result then only
                        searchFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_BACK ->{
                        searchTab.requestFocus()
                    }
                }
            }
            true
        }

        allTab.requestFocus()
        loadFrag(allFragment, flag)
        changeSelected(0,0)
        flag = 1

//        loadFrag(PlayerFragment(),0)

        //on Focus changes tracker
        allTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                changeSelected(selectedTab,0)
                loadFrag(allFragment, flag)
            }
        }

        recentTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                changeSelected(selectedTab,1)
                loadFrag(recentFragment, flag)
            }
        }

        watchlist.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                changeSelected(selectedTab,2)
                loadFrag(watchListFragment, flag)
            }
        }

        searchTab.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                changeSelected(selectedTab,3)
                loadFrag(searchFragment, flag)
            }
        }


        allTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN)) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        //will do nothing here
                    }

                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        //send focus to mainRv
                        allFragment.focus(0, 0)
                    }

                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        //do nothing
                    }

                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        //send focus to recent tab
                        recentTab.requestFocus()
                    }

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        //send focus to mainRv

                    }
                    KeyEvent.KEYCODE_BACK -> {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastBackPressTime < 3000) {
                            this.finish()
                        } else {
                            lastBackPressTime = currentTime
                            Toast.makeText(v.context, "Press back again to exit", Toast.LENGTH_SHORT).show()

                        }
                        return@OnKeyListener true
                    }
                }
            }
            true
        })

        recentTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                Log.d("TAB", "onCreate: KEY Pressed")
                when (keyCode) {
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

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        //send focus to grid
                        recentFragment.focus(0)
                    }
                    KeyEvent.KEYCODE_BACK -> {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastBackPressTime < 3000) {
                            this.finish()
                        } else {
                            lastBackPressTime = currentTime
                            Toast.makeText(v.context, "Press back again to exit", Toast.LENGTH_SHORT).show()

                        }
                        return@OnKeyListener true
                    }
                }
            }
            true
        })

        watchlist.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                Log.d("TAB", "onCreate: KEY Pressed")

                when (keyCode) {
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

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        //send focus to grid
                        watchListFragment.focus(0)

                    }
                    KeyEvent.KEYCODE_BACK -> {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastBackPressTime < 3000) {
                            this.finish()
                        } else {
                            lastBackPressTime = currentTime
                            Toast.makeText(v.context, "Press back again to exit", Toast.LENGTH_SHORT).show()

                        }
                        return@OnKeyListener true
                    }
                }
            }
            true
        })

        searchTab.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                Log.d("TAB", "onCreate: KEY Pressed")
                Log.d("TAB", "onCreate: ${currentFocus}")
                when (keyCode) {
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

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        //search then send focus to gridV
                        searchView.requestFocus()
                    }
                    KeyEvent.KEYCODE_BACK -> {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastBackPressTime < 3000) {
                            this.finish()
                        } else {
                            lastBackPressTime = currentTime
                            Toast.makeText(v.context, "Press back again to exit", Toast.LENGTH_SHORT).show()

                        }
                        return@OnKeyListener true
                    }
                }
            }
            true
        })

    }

    fun loadFrag(frag: Fragment?, flag: Int) {
        val fm: FragmentManager = supportFragmentManager
        val ft = fm.beginTransaction()

        if (flag == 0) {
            frag?.let { ft.add(R.id.container, it) }
            ft.addToBackStack(null)
        } else if (flag == 1) {
            if (frag != null) {
                ft.replace(R.id.container, frag)
            }
        } else if (flag == 2) {
            if (frag != null) {
                ft.replace(R.id.container, frag).addToBackStack(null)
            }
        } else {
            //pop out last one
            Log.d("BACK", "loadFrag: BACK")
            fm.popBackStack()
        }
        ft.commit()
    }

    fun loadDetailFrag(show: Show, tab: Int, rowPosition: Int, childPos: Int) {
        detailFragment = DetailFragment(show, mainInterface, tab, rowPosition, childPos)
        loadFrag(detailFragment, 0)
    }

    fun loadPlayerFrag(show: Show, tab: Int) {
        loadFrag(PlayerFragment(show, tab, mainInterface), 2)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun insertInitialdata() {
        //first row
        val listShow1 = listOf(
            Show(
                "Big Buck Bunny",
                "Big Buck Bunny tells the story of a giant rabbit with a heart bigger than himself. When one sunny day three rodents rudely harass him, something snaps... and the rabbit ain't no bunny anymore! In the typical cartoon tradition he prepares the nasty rodents a comical revenge.",
                "Romance",
                2020,
                "1h 30m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
                "Top Picks"
            ),
            Show(
                "Elephant Dream",
                "The first Blender Open Movie from 2006",
                "Romance",
                2021,
                "1h 35m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg",
                "Top Picks"
            ),
            Show(
                "For Bigger Blazes",
                "HBO GO now works with Chromecast -- the easiest way to enjoy online video on your TV. For when you want to settle into your Iron Throne to watch the latest episodes. For \$35.\\nLearn how to use Chromecast with HBO GO and more at google.com/chromecast.",
                "Action",
                2005,
                "1h 40m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerBlazes.jpg",
                "Top Picks"
            ),
            Show(
                "For Bigger Escape",
                "TIntroducing Chromecast. The easiest way to enjoy online video and music on your TV—for when Batman's escapes aren't quite big enough. For \$35. Learn how to use Chromecast with Google Play Movies and more at google.com/chromecast.",
                "Thriller",
                2023,
                "2h 30m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerEscapes.jpg",
                "Top Picks"
            ),
            Show(
                "For Bigger Fun",
                "Introducing Chromecast. The easiest way to enjoy online video and music on your TV. For \$35.  Find out more at google.com/chromecast",
                "Horror",
                2018,
                "1h 23m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerFun.jpg",
                "Top Picks"
            )
        )
        //second row
        val listShow2 = listOf(
            Show(
                "For Bigger Joyrides",
                "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for the times that call for bigger joyrides. For $35. Learn how to use Chromecast with YouTube and more at google.com/chromecast.",
                "Drama",
                2010,
                "1h 23m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerJoyrides.jpg",
                "Trending"
            ),
            Show(
                "For Bigger Meltdowns",
                "Introducing Chromecast. The easiest way to enjoy online video and music on your TV—for when you want to make Buster's big meltdowns even bigger. For $35. Learn how to use Chromecast with Netflix and more at google.com/chromecast.",
                "Sci-Fi",
                2014,
                "2h 35m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ForBiggerMeltdowns.jpg",
                "Trending"
            ),
            Show(
                "Sintel",
                "Sintel is an independently produced short film, initiated by the Blender Foundation as a means to further improve and validate the free/open source 3D creation suite Blender. With initial funding provided by 1000s of donations via the internet community, it has again proven to be a viable development model for both open 3D technology as for independent animation film.\nThis 15 minute film has been realized in the studio of the Amsterdam Blender Institute, by an international team of artists and developers. In addition to that, several crucial technical and creative targets have been realized online, by developers and artists and teams all over the world.\nwww.sintel.org",
                "Thriller",
                2016,
                "1h 27m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/Sintel.jpg",
                "Trending"
            ),
            Show(
                "Subaru Outback On Street And Dirt",
                "Smoking Tire takes the all-new Subaru Outback to the highest point we can find in hopes our customer-appreciation Balloon Launch will get some free T-shirts into the hands of our viewers.",
                "Horror",
                2020,
                "1h 45m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/SubaruOutbackOnStreetAndDirt.jpg",
                "Trending"
            ),
            Show(
                "Tears of Steel",
                "Tears of Steel was realized with crowd-funding by users of the open source 3D creation tool Blender. Target was to improve and test a complete open and free pipeline for visual effects in film - and to make a compelling sci-fi film in Amsterdam, the Netherlands.  The film itself, and all raw material used for making it, have been released under the Creatieve Commons 3.0 Attribution license. Visit the tearsofsteel.org website to find out more about this, or to purchase the 4-DVD box with a lot of extras.  (CC) Blender Foundation - http://www.tearsofsteel.org",
                "Romance",
                2022,
                "2h 30m",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/TearsOfSteel.jpg",
                "Trending"

            )
        )

        for (show in listShow1){
            showRowViewModel.insert(show)
        }
        for (show in listShow2){
            showRowViewModel.insert(show)
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addToRecent(show: Show) {
        showRowViewModel.update(show)
    }

    fun changeSelected(prev:Int,curr:Int){
        when(prev){
            0 ->{
                allTab.setCardBackgroundColor(resources.getColor(R.color.focused_color_show))
            }
            1->{
                recentTab.setCardBackgroundColor(resources.getColor(R.color.focused_color_show))

            }
            2->{
                watchlist.setCardBackgroundColor(resources.getColor(R.color.focused_color_show))

            }
            3->{
                searchTab.setCardBackgroundColor(resources.getColor(R.color.focused_color_show))

            }
        }

        when(curr){
            0 ->{
                allTab.setCardBackgroundColor(resources.getColor(R.color.pressed_color))
            }
            1->{
                recentTab.setCardBackgroundColor(resources.getColor(R.color.pressed_color))

            }
            2->{
                watchlist.setCardBackgroundColor(resources.getColor(R.color.pressed_color))

            }
            3->{
                searchTab.setCardBackgroundColor(resources.getColor(R.color.pressed_color))

            }
        }

        selectedTab=curr
    }

    interface MainInterface {
        fun onKeyUp(tab: Int)
        fun onKeyCenter(show: Show, tab: Int, rowPosition: Int, childPos: Int)

        fun OnKeyBack(tab: Int)

        fun onKeyCenterPlay(show: Show, tab: Int, rowPosition: Int, childPos: Int)
    }


}