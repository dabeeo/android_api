package com.dabeeo.indoor.sample.view.basic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivitySimpleViewerBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.MapInfo

class SimpleViewerActivity : AppCompatActivity() {

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {

        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySimpleViewerBinding>(
            this,
            R.layout.activity_simple_viewer
        ).apply {
            mMapView.getMapSync(listOf(mMapEvent), AUTHORIZATION)
        }
    }
}