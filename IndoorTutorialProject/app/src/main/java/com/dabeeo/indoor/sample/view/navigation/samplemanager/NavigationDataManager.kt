package com.dabeeo.indoor.sample.view.navigation.samplemanager

import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.dabeeo.indoor.sample.R
import com.dabeeo.maps.basictype.Image
import com.dabeeo.maps.basictype.Location
import com.dabeeo.maps.basictype.Point
import com.dabeeo.maps.draw.Display
import com.dabeeo.maps.draw.Marker
import com.dabeeo.maps.draw.Polyline
import com.dabeeo.maps.event.NavigationEvent
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.navigation.Navigation
import com.dabeeo.maps.navigation.NavigationMode
import com.dabeeo.maps.navigation.NavigationOptions
import com.dabeeo.maps.navigation.Routes

class NavigationDataManager constructor(
    private val mINavigationDataManagerCallback: INavigationDataManagerCallback,
    private val mPreviewEvent: NavigationEvent
) {

    interface INavigationDataManagerCallback {
        fun onReady()
        fun onError(message: String)
    }

    private var mOriginLocation: Location? = null
    private var mDestinationLocation: Location? = null
    private var mNavigation: Navigation? = null

    private var mPathline: Polyline? = null
    private var mOriginMarker: Marker? = null
    private var mDestinationMarker: Marker? = null

    private var mIsRunning = false

    fun setRunning(status: Boolean) {
        mIsRunning = status
    }

    fun setOrigin(mapView: MapView, location: Location) {
        if (mOriginMarker == null) {
            mOriginMarker = getMarker(mapView, location, R.drawable.start_mark)
        } else {
            mOriginMarker!!.position = Point(location.x, location.y, 0.0)
        }
        mOriginMarker?.addTo()
        mOriginLocation = location
        isReady()
    }

    fun setDestination(mapView: MapView, location: Location) {
        if (mDestinationMarker == null) {
            mDestinationMarker = getMarker(mapView, location, R.drawable.end_mark)
        } else {
            mDestinationMarker!!.position = Point(location.x, location.y, 0.0)
        }
        mDestinationMarker?.addTo()
        mDestinationLocation = location
        isReady()
    }

    fun start() {
        mNavigation?.startDirections()
            ?: mINavigationDataManagerCallback.onError("출/도착지를 먼저 설정해주세요.")
    }

    fun cancel() {
        mNavigation?.cancelDirections()
    }

    fun makeNavigation(mapView: MapView, navigationMode: NavigationMode) {
        hidePathAndMarkers()
        mNavigation = Navigation(
            mOriginLocation,
            mDestinationLocation,
            mapView,
            mPreviewEvent,
            when (navigationMode) {
                NavigationMode.GUIDANCE -> getGuidanceOptions(mapView)
                NavigationMode.SIMULATE -> getSimulateOptions(mapView)
                NavigationMode.PREVIEW -> getPreviewOptions(mapView)
            }
        )
    }

    fun drawPath(mapView: MapView) {
        if (mIsRunning) {
            return
        }
        drawMarkers(mapView)
        val routes = getRoutes() ?: return
        val pathPointList = routes.sectionList.let { sectionList ->
            val points = mutableListOf<Point>()
            sectionList.forEach { section ->
                if (section.floorLevel == mapView.floor.level) {
                    section.pathList.let { nlList ->
                        nlList.forEach { nl ->
                            points.add(Point(nl.x, nl.y, 0.0))
                        }
                        points
                    }
                }
            }
            points
        }
        if (mPathline == null) {
            mPathline = Polyline(pathPointList, mapView).apply {
                color = "#000000"
            }
        } else {
            mPathline!!.points = pathPointList
        }
        mPathline?.isVisible = true
        mPathline!!.addTo()
    }

    private fun drawMarkers(mapView: MapView) {
        if (mOriginLocation != null
            && mapView.floor.level == mOriginLocation!!.floorLevel
        ) {
            mOriginMarker!!.addTo()
        }
        if (mDestinationLocation != null
            && mapView.floor.level == mDestinationLocation!!.floorLevel
        ) {
            mDestinationMarker!!.addTo()
        }
        mOriginMarker?.displayType = Display.ALL
        mDestinationMarker?.displayType = Display.ALL
    }

    fun hidePathAndMarkers() {
        mPathline?.isVisible = false
        mOriginMarker?.displayType = Display.NONE
        mDestinationMarker?.displayType = Display.NONE
    }

    private fun getRoutes(): Routes? {
        return mNavigation?.routes ?: null
    }

    private fun isReady() {
        if (mOriginLocation == null) {
            return
        }
        if (mDestinationLocation == null) {
            return
        }
        mINavigationDataManagerCallback.onReady()
    }

    private fun getGuidanceOptions(mapView: MapView): NavigationOptions {
        return NavigationOptions(mapView, mapView.context).apply {
            mode = NavigationMode.GUIDANCE
        }
    }

    private fun getSimulateOptions(mapView: MapView): NavigationOptions {
        return NavigationOptions(mapView, mapView.context).apply {
            mode = NavigationMode.SIMULATE
        }
    }

    private fun getPreviewOptions(mapView: MapView): NavigationOptions {
        return NavigationOptions(mapView, mapView.context).apply {
            mode = NavigationMode.PREVIEW
        }
    }

    private fun getMarker(
        mapView: MapView,
        location: Location,
        @DrawableRes drawable: Int
    ): Marker {
        return Marker(mapView.context, Point(location.x, location.y, 0.0), mapView).apply {
            icon = Image(BitmapFactory.decodeResource(mapView.context!!.resources, drawable))
            icon.width = 50.0
            icon.height = 50.0
        }
    }
}