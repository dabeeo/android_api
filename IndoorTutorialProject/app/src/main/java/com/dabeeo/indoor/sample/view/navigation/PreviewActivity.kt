package com.dabeeo.indoor.sample.view.navigation

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivityPreviewBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.indoor.sample.util.DialogFactory
import com.dabeeo.indoor.sample.view.navigation.samplemanager.NavigationDataManager
import com.dabeeo.maps.basictype.Location
import com.dabeeo.maps.basictype.Point
import com.dabeeo.maps.draw.Marker
import com.dabeeo.maps.event.DrawEvent
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.event.NavigationEvent
import com.dabeeo.maps.event.Reason
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.FloorInfo
import com.dabeeo.maps.indoormap.data.MapInfo
import com.dabeeo.maps.navigation.NavigationMode
import com.dabeeo.maps.navigation.Routes

class PreviewActivity : AppCompatActivity() {


    private val mINavigationDataManagerCallback: NavigationDataManager.INavigationDataManagerCallback =
        object : NavigationDataManager.INavigationDataManagerCallback {
            override fun onReady() {
                mNavigationDataManager.makeNavigation(mMapView, NavigationMode.PREVIEW)
                mNavigationDataManager.drawPath(mMapView)
            }

            override fun onError(message: String) {
                toastError(message)
            }
        }

    private val mPreviewEvent: NavigationEvent = object : NavigationEvent() {
        override fun directionsBegin(routes: Routes?) {
            mNavigationDataManager.setRunning(true)
            mNavigationDataManager.hidePathAndMarkers()
        }

        override fun directionAnimations(routes: Routes?) {

        }

        override fun directionsEnd(routes: Routes?) {
            mNavigationDataManager.setRunning(false)
            mNavigationDataManager.drawPath(mMapView)
        }

        override fun error(code: String?, message: String) {
            mNavigationDataManager.setRunning(false)
            toastError(message)
        }
    }

    private val mNavigationDataManager =
        NavigationDataManager(mINavigationDataManagerCallback, mPreviewEvent)

    private lateinit var mBinding: ActivityPreviewBinding

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            mBinding.btnStart.setOnClickListener { mNavigationDataManager.start() }
            mBinding.btnCancel.setOnClickListener { mNavigationDataManager.cancel() }
            initPoiEvent()
        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }

        override fun floorEnd(floorInfo: FloorInfo?, reason: Reason?) {
            initPoiEvent()
            mNavigationDataManager.drawPath(mMapView)
        }

        override fun longClick(point: Point) {
            DialogFactory.createLocationDialog(
                this@PreviewActivity,
                "\n선택위치\nLocation = ${point.x.toInt()}, ${point.y.toInt()}",
                DialogInterface.OnClickListener { _, _ ->
                    mNavigationDataManager.setOrigin(
                        mMapView,
                        Location(point.x, point.y, mMapView.floor.level)
                    )
                },
                DialogInterface.OnClickListener { _, _ ->
                    mNavigationDataManager.setDestination(
                        mMapView,
                        Location(point.x, point.y, mMapView.floor.level)
                    )
                }
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView<ActivityPreviewBinding>(this, R.layout.activity_preview)
                .apply {
                    mMapView.getMapSync(listOf(mMapEvent), AUTHORIZATION)
                }
    }

    private val mMarkerEvent = object : DrawEvent<Marker>() {
        override fun click(drawObject: Marker) {
            DialogFactory.createLocationDialog(
                this@PreviewActivity,
                "\n${drawObject.title}\nLocation = ${drawObject.position.x.toInt()}, ${drawObject.position.y.toInt()}",
                DialogInterface.OnClickListener { _, _ ->
                    mNavigationDataManager.setOrigin(
                        mMapView,
                        Location(drawObject.position.x, drawObject.position.y, mMapView.floor.level)
                    )
                },
                DialogInterface.OnClickListener { _, _ ->
                    mNavigationDataManager.setDestination(
                        mMapView,
                        Location(drawObject.position.x, drawObject.position.y, mMapView.floor.level)
                    )
                }
            ).show()
        }
    }

    private fun initPoiEvent() {
        mMapView?.poiData?.forEach { marker ->
            marker.event = mMarkerEvent
        }
    }
}