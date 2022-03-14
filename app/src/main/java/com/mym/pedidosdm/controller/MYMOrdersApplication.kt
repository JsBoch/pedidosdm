package com.mym.pedidosdm.controller

import android.app.Application

class MYMOrdersApplication:Application() {
    companion object{
        lateinit var prefs:Preferences
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(applicationContext)
    }
}