package com.dabeeo.indoor.sample.view.animation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivityMapAnimationBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.maps.basictype.Point
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.MapInfo
import kotlin.random.Random

class MapAnimationActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMapAnimationBinding
    private val mRandom = Random(1004)

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            mapInfo?.apply {
                mBinding.btnMove.setOnClickListener {
                    mMapView.center = Point(
                        mRandom.nextDouble(mapInfo.size.width),
                        mRandom.nextDouble(mapInfo.size.height),
                        0.0
                    )
                }
                mBinding.btnZoom.setOnClickListener {
                    mMapView.zoom = mRandom.nextDouble(11.0)
                }
                mBinding.btnMoveAndZoom.setOnClickListener {
                    val movePoint = Point(
                        mRandom.nextDouble(mapInfo.size.width),
                        mRandom.nextDouble(mapInfo.size.height),
                        0.0
                    )
                    val zoomLevel = mRandom.nextDouble(11.0)
                    mMapView.setView(movePoint, zoomLevel, true)
                }
            }
        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityMapAnimationBinding>(
            this,
            R.layout.activity_map_animation
        ).apply {
            mMapView.getMapSync(listOf(mMapEvent), AUTHORIZATION)
        }
    }
}