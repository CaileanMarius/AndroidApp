package com.CaileanMarius.androidapplication

import android.icu.util.ValueIterator
import android.util.Log

object VideoGameRepository {

    private var cachedVideoGame: MutableList<VideoGame>? = null;

    suspend fun loadAll(): List<VideoGame>{
        Log.i(TAG, "loadAll")
        if(cachedVideoGame!=null)
        {
            return  cachedVideoGame as List<VideoGame>;
        }
        cachedVideoGame = mutableListOf()
        val videogames = VideoGameApi.service.find()
        cachedVideoGame?.addAll(videogames)
        return cachedVideoGame as List<VideoGame>
    }

    suspend fun load(videogameId: String): VideoGame{
        Log.i(TAG, "load")
        val videoGame = cachedVideoGame?.find{it.id == videogameId}
        if(videoGame != null)
        {
            return  videoGame
        }
        return  VideoGameApi.service.read(videogameId)
    }

    suspend fun save(videogame: VideoGame): VideoGame{
        Log.i(TAG, "Save")
        val createdVideoGame = VideoGameApi.service.create(videogame)
        cachedVideoGame?.add(createdVideoGame)
        return  createdVideoGame
    }

    suspend fun update(videogame: VideoGame): VideoGame{
        Log.i(TAG, "update")
        val updatedVideoGame = VideoGameApi.service.update(videogame.id, videogame)
        val index = cachedVideoGame?.indexOfFirst { it.id == videogame.id }
        if (index!=null)
        {
            cachedVideoGame?.set(index, updatedVideoGame)
        }
        return  updatedVideoGame
    }


}