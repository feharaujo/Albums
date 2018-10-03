package com.fearaujo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fearaujo.data.model.Album
import com.fearaujo.data.model.ResultModel
import com.fearaujo.data.test.EspressoIdlingResource
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

const val URL_BASE = "http://jsonplaceholder.typicode.com"

class RepositoryImpl : Repository {

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(jacksonConverter)
                .build()

        retrofit.create(ApiService::class.java)
    }

    private val jacksonConverter: GsonConverterFactory by lazy {
        GsonConverterFactory.create()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private var cacheData: ResultModel? = null

    override fun fetchAlbums(): LiveData<ResultModel> {
        val responseLiveData = MutableLiveData<ResultModel>()

        EspressoIdlingResource.increment()

        if (cacheData == null) {
            fetchRemote(responseLiveData)
        } else {
            responseLiveData.postValue(cacheData)
            EspressoIdlingResource.decrement()
        }

        return responseLiveData
    }

    private fun fetchRemote(responseLiveData: MutableLiveData<ResultModel>) {
        apiService.fetchAlbums().enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                responseLiveData.postValue(ResultModel(null, true))

                EspressoIdlingResource.decrement()
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                val responseList = response.body()
                if (responseList != null) {
                    // order
                    val sortedList = responseList.sortedWith(compareBy(Album::title))

                    // save cache
                    cacheData = ResultModel(sortedList, false)
                    responseLiveData.postValue(cacheData)
                } else {
                    responseLiveData.postValue(ResultModel(null, true))
                }

                EspressoIdlingResource.decrement()
            }

        })
    }

}