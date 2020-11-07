package com.CaileanMarius.androidapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object VideoGameApi {
    private const val URL = "http://192.168.100.7:3000/"

    interface Service{
        @GET("/videogame")
        suspend fun find(): List<VideoGame>

        @GET("videogame/{id}")
        suspend fun read(@Path("id") videogameId: String): VideoGame

        @Headers("Content-Type: application/json")
        @POST("/videogame")
        suspend fun create(@Body videogame: VideoGame): VideoGame

        @Headers("Content-Type: application/json")
        @PUT("/videogame/{id}")
        suspend fun update(@Path("id") videogameId: String, @Body videogame: VideoGame): VideoGame

    }

    private val client: OkHttpClient = OkHttpClient.Builder().build()

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private  val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val service: Service = retrofit.create(Service::class.java)

}