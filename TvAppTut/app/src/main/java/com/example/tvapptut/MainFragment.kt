package com.example.tvapptut

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter

class MainFragment : BrowseSupportFragment() {
    private lateinit var mRowsAdapter: ArrayObjectAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRows()
        title="Hello Android Tv"
        headersState= HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled=true
        brandColor= context?.resources?.getColor(R.color.fastlane_background)!!
        searchAffordanceColor=context?.resources?.getColor(R.color.search_opaque)!!

    }

    private fun loadRows(){

//        mRowsAdapter=ArrayObjectAdapter(ListRowPresenter())
//       val gridItemPresenterHeader=HeaderItem(0,"GridItemPresenter")
//
//        val mGridItemPresenter=GridItemPresenter()
//        val gridRowAdapter=ArrayObjectAdapter(mGridItemPresenter)
//
//        gridRowAdapter.add("ITEM 1")
//        gridRowAdapter.add("ITEM 2")
//        gridRowAdapter.add("ITEM 3")
//
//        mRowsAdapter.add(ListRow(gridItemPresenterHeader,gridRowAdapter))




        mRowsAdapter=ArrayObjectAdapter(ListRowPresenter())

        val cardPresenterHeader=HeaderItem(0,"cardPresenter")

        val cardPresenter=CardPresenter()
        val cardRowAdapter=ArrayObjectAdapter(cardPresenter)

        for (i in 1..10){
            cardRowAdapter.add(Movie(i.toLong(),"title $i","studio $i"))
        }

        mRowsAdapter.add(ListRow(cardPresenterHeader,cardRowAdapter))

        adapter=mRowsAdapter
    }


}