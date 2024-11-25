package com.example.myshoppinglistapp


import android.app.Application

class ItemListApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}