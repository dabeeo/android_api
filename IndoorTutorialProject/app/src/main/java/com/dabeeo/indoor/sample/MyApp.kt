package com.dabeeo.indoor.sample

import android.app.Application
import com.dabeeo.maps.basictype.Authorization

class MyApp : Application() {

    companion object {
        lateinit var INSTANCE: Application
        val AUTHORIZATION =
            Authorization("28AXw_veA2YbNKDP6poTpT", "70c540c169af62808f4da3709e988e06")
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}