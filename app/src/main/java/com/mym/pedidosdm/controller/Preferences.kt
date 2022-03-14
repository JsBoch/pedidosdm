package com.mym.pedidosdm.controller

import android.content.Context

class Preferences(val context:Context) {
    val SHARED_NAME = "loginPreferences"
    var SHARED_USER_NAME = "userName"
    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveName(name:String)
    {
        storage.edit().putString(SHARED_USER_NAME,name).apply()
    }

    fun getName():String {
       return storage.getString(SHARED_USER_NAME,"")!!
    }
}