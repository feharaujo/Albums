package com.fearaujo.data

import com.fearaujo.data.model.Album
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/albums")
    fun fetchAlbums(): Call<List<Album>>

}