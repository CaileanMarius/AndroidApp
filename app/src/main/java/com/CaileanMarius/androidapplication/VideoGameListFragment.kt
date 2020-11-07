package com.CaileanMarius.androidapplication

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_videogame_list.*

class VideoGameListFragment : Fragment() {
    private lateinit var videogameListAdapter: VideoGameListAdapter
    private lateinit var videogamesModel: VideoGameListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG,"onCreate");
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videogame_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener{
            Log.v(TAG,"creating new videogame")
            videogamesModel.videogames.value?.size?.let {videogamesModel.createVideoGame(it)}
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated")
        setUpVideoGameList()
        fab.setOnClickListener {
            Log.v(TAG, "add new item")
            findNavController().navigate(R.id.VideoGameEditFragment)
        }
    }

    private fun setUpVideoGameList()
    {
        videogameListAdapter = VideoGameListAdapter(this)
        videogame_list.adapter = videogameListAdapter
        videogamesModel = ViewModelProvider(this).get(VideoGameListViewModel::class.java)
        videogamesModel.videogames.observe(viewLifecycleOwner, {videogames ->
            Log.i(TAG,"update videogames")
            videogameListAdapter.videogames = videogames
        })
        videogamesModel.loading.observe(viewLifecycleOwner, {loading ->
            Log.i(TAG,"update loading")
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        })
        videogamesModel.loadingError.observe(viewLifecycleOwner, {exception ->
            if(exception!=null){
                Log.i(TAG, "update loading error")
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })
        videogamesModel.loadVideoGames()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy");
    }

}