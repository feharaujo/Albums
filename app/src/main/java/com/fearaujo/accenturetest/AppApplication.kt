package com.fearaujo.accenturetest

import android.app.Application
import com.fearaujo.data.Repository
import com.fearaujo.data.RepositoryImpl

class AppApplication : Application() {

    val repository: Repository by lazy {
        RepositoryImpl()
    }

    override fun onCreate() {
        super.onCreate()
    }

}