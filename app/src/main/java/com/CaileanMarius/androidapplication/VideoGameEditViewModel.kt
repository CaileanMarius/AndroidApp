package com.CaileanMarius.androidapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class VideoGameEditViewModel: ViewModel() {
    private val mutableVideoGame = MutableLiveData<VideoGame>().apply { value = VideoGame("", "","","","") }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val videoGame: LiveData<VideoGame> = mutableVideoGame
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadVideoGame(videoGameId: String){
        viewModelScope.launch {
            Log.i(TAG, "loadVideogame..")
            mutableFetching.value = true
            mutableException.value = null
            try{
                mutableVideoGame.value = VideoGameRepository.load(videoGameId)
                Log.i(TAG, "loadVideoGame succeeded")
                mutableFetching.value = false
            }
            catch (e: Exception){
                Log.w(TAG, "loadVideogame failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(text: String,  videoGameYear: String, videoGameType: String, videoGameRating: String)
    {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem..");
            val videogame = mutableVideoGame.value?:return@launch
            videogame.description = text
            videogame.year = videoGameYear
            videogame.type = videoGameType
            videogame.rating = videoGameRating
            mutableFetching.value = true
            mutableException.value = null
            try{
                if(videogame.id.isNotEmpty()){
                    mutableVideoGame.value = VideoGameRepository.update(videogame)

                }
                else
                {
                    mutableVideoGame.value = VideoGameRepository.save(videogame)
                }
                Log.i(TAG, "saveOrUpdateItem succeeded")
                mutableCompleted.value = true
                mutableFetching.value = false

            }
            catch (e: Exception){
                Log.w(TAG, "saveOrUpdateItem failed", e );
                mutableException.value = e
                mutableFetching.value = false
            }

        }
    }

}