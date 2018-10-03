package com.fearaujo.accenturetest.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fearaujo.accenturetest.AppApplication
import com.fearaujo.accenturetest.R
import com.fearaujo.data.model.Album
import com.fearaujo.mememaker.arch.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private val adapter: AlbumAdapter by lazy {
        AlbumAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter?.fetchData()
        errorText.setOnClickListener {
            presenter?.fetchData()
        }
    }

    override fun initPresenter(): MainContract.Presenter {
        val application = application as AppApplication
        return MainPresenter(this, application.repository)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showErrorMsg() {
        errorText.visibility = View.VISIBLE
    }

    override fun hideErrorMsg() {
        errorText.visibility = View.GONE
    }

    override fun showRecyclerView() {
        recyclerView.visibility = View.VISIBLE
    }

    override fun loadData(data: List<Album>) {
        adapter.update(data)
    }

    override fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


}
