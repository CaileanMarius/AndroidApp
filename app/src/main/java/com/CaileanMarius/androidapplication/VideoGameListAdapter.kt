package com.CaileanMarius.androidapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_videogame.view.*

class VideoGameListAdapter (
    private val fragment: Fragment
): RecyclerView.Adapter<VideoGameListAdapter.ViewHolder>(){

    var videogames = emptyList<VideoGame>()
        set(value){
            field = value
            notifyDataSetChanged();
        }

    private  var OnVideoGameClick: View.OnClickListener;

    init {
        OnVideoGameClick = View.OnClickListener { view ->
            val videogame = view.tag as VideoGame
            fragment.findNavController().navigate(R.id.VideoGameEditFragment, Bundle().apply {
                putString(VideoGameEditFragment.VIDEOGAME_ID, videogame.id)
            })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_videogame,parent,false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        Log.v(TAG,"onBindViewHolder $position")
        val videogame = videogames[position]
        holder.itemView.tag = videogame
        holder.textView.text = videogame.description
        holder.itemView.setOnClickListener(OnVideoGameClick)

    }

    override fun getItemCount() = videogames.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textView: TextView  = view.description
    }

}
