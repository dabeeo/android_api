package com.dabeeo.indoor.sample.view.event

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivitySimpleViewerBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.indoor.sample.toastMessage
import com.dabeeo.maps.draw.Marker
import com.dabeeo.maps.event.DrawEvent
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.event.Reason
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.FloorInfo
import com.dabeeo.maps.indoormap.data.MapInfo

class PoiEventActivity : AppCompatActivity() {

    private val mMarkerEvent = object : DrawEvent<Marker>() {
        override fun click(drawObject: Marker?) {
            toastMessage("You clicked below item.\n${drawObject?.title}(${drawObject?.id})")
        }
    }

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            initPoiEvent()
        }

        override fun floorEnd(floorInfo: FloorInfo?, reason: Reason?) {
            initPoiEvent()
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

    private fun initPoiEvent() {
        mMapView?.poiData?.forEach { marker ->
            marker.event = mMarkerEvent
        }
    }
}