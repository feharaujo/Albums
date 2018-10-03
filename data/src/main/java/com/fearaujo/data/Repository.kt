package com.fearaujo.data

import androidx.lifecycle.LiveData
import com.fearaujo.data.model.ResultModel

interface Repository {

    fun fetchAlbums(): LiveData<ResultModel>

}
