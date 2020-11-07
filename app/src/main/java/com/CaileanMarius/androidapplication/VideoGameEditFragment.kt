package com.CaileanMarius.androidapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_videogame_edit.*
import kotlinx.android.synthetic.main.fragment_videogame_list.*
import kotlinx.android.synthetic.main.fragment_videogame_list.fab
import kotlinx.android.synthetic.main.fragment_videogame_list.progress
import kotlinx.android.synthetic.main.view_videogame.view.*

class VideoGameEditFragment : Fragment() {
    companion object{
        const val VIDEOGAME_ID = "VIDEOGAME_ID"
        //

    }

    private lateinit var viewModel: VideoGameEditViewModel
    private var videogameId: String? =null
    //


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        arguments?.let{
            if (it.containsKey(VIDEOGAME_ID))
            {
                videogameId = it.getString(VIDEOGAME_ID).toString()
                //



            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_videogame_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "onViewCreated")

        videogame_description.setText(videogameId)
        //

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save item")
            viewModel.saveOrUpdateItem(videogame_description.text.toString(),videogame_year.text.toString(), videogame_type.text.toString(), videogame_rating.text.toString())
        }

    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this).get(VideoGameEditViewModel::class.java)
        viewModel.videoGame.observe(viewLifecycleOwner, { videogame ->
            Log.v(TAG, "update items")
            videogame_description.setText(videogame.description)
            videogame_year.setText(videogame.year)
            videogame_type.setText(videogame.type)
            videogame_rating.setText(videogame.rating)
        })
        viewModel.fetching.observe(viewLifecycleOwner,{fetching->
            Log.v(TAG, "update fetching")
            progress.visibility = if(fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
        })
        val id = videogameId
        if (id != null) {
            viewModel.loadVideoGame(id)
        }

    }



}