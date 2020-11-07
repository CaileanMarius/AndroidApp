package com.CaileanMarius.androidapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.FieldPosition

class VideoGameListViewModel : ViewModel() {
    private  val mutableVideoGames = MutableLiveData<List<VideoGame>>().apply {  value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }


    val videogames: LiveData<List<VideoGame>> = mutableVideoGames
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createVideoGame(position: Int): Unit{
        val list = mutableListOf<VideoGame>()
        list.addAll(mutableVideoGames.value!!)
        list.add(VideoGame(position.toString(),"VideoGame " +position,"2000","sf","5"))
        mutableVideoGames.value = list
    }

    fun loadVideoGames(){
        viewModelScope.launch {
            Log.v(TAG, "loadVideoGames..");
            mutableLoading.value = true
            mutableException.value = null
            try{
                mutableVideoGames.value = VideoGameRepository.loadAll()
                Log.d(TAG, "loadItems succeeded")
                mutableLoading.value = false
            }
            catch (e: Exception){
                Log.w(TAG, "loadVideoGames failed", e)
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }




}