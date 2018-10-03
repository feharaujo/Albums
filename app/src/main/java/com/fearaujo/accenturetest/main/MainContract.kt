package com.fearaujo.accenturetest.main

import com.fearaujo.data.model.Album
import com.fearaujo.mememaker.arch.BaseContract

class MainContract : BaseContract {

    interface View : BaseContract.View {
        fun showProgressBar()
        fun hideProgressBar()

        fun showErrorMsg()
        fun hideErrorMsg()

        fun setupRecyclerView()
        fun showRecyclerView()
        fun loadData(data: List<Album>)

    }

    interface Presenter : BaseContract.Presenter<MainContract.View> {
        fun fetchData()
    }
}