package com.CaileanMarius.androidapplication

data class VideoGame(
    var id: String,
    var description: String,
    var year: String,
    var type: String,
    var rating: String
){
    override fun toString(): String = description
    }
