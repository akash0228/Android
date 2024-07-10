package com.example.tweetsy.repository

import com.example.tweetsy.api.TweetsyAPI
import com.example.tweetsy.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetsyAPI: TweetsyAPI) {
    private val _categories= MutableStateFlow<List<String>>(emptyList()) //we are not exposing this public because this is mutable then any one can push data in it
    val categories:StateFlow<List<String>> //we are exposing immutable stateFlow so only read can be done
        get() = _categories

    private val _tweets= MutableStateFlow<List<TweetListItem>>(emptyList()) //we are not exposing this public because this is mutable then any one can push data in it
    val tweets:StateFlow<List<TweetListItem>> //we are exposing immutable stateFlow so only read can be done
        get() = _tweets

    suspend fun getCategories(){
        val response=tweetsyAPI.getCategories()
        if (response.isSuccessful && response.body()!=null){
            _categories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String){
        val response=tweetsyAPI.getTweets("tweets[?(@.category==\"$category\")]")
        if (response.isSuccessful && response.body()!=null){
            _tweets.emit(response.body()!!)
        }
    }
}