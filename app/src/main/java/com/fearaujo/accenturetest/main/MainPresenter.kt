package com.fearaujo.accenturetest.main

import com.fearaujo.data.Repository
import com.fearaujo.data.model.Album
import com.fearaujo.mememaker.arch.BasePresenter

class MainPresenter(override var view: MainContract.View?, val repository: Repository) :
        BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun fetchData() {
        showLoading()

        repository.fetchAlbums().observeForever { resultModel ->
            if (resultModel.error) {
                showErrorMsg()
            } else {
                resultModel.list?.let { loadDataRecyclerView(it) }
            }
        }
    }

    private fun showErrorMsg() {
        view?.hideProgressBar()
        view?.showErrorMsg()
    }

    private fun loadDataRecyclerView(list: List<Album>) {
        view?.setupRecyclerView()

        view?.hideProgressBar()
        view?.hideErrorMsg()
        view?.showRecyclerView()

        view?.loadData(list)
    }

    private fun showLoading() {
        view?.hideErrorMsg()
        view?.showProgressBar()
    }

}
