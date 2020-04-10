package com.dabeeo.indoor.sample.view.vps

import android.Manifest
import android.animation.AnimatorSet
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivityVpsBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.indoor.sample.view.vps.data.ContentInfo
import com.dabeeo.maps.basictype.Image
import com.dabeeo.maps.basictype.Location
import com.dabeeo.maps.basictype.Point
import com.dabeeo.maps.draw.Marker
import com.dabeeo.maps.draw.MarkerOptions
import com.dabeeo.maps.event.DrawEvent
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.event.Reason
import com.dabeeo.maps.event.VPSEvent
import com.dabeeo.maps.indoormap.MapOptions
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.FloorInfo
import com.dabeeo.maps.indoormap.data.MapInfo
import com.dabeeo.maps.vps.VPSOptions
import com.dabeeo.maps.vps.enums.DirectionType
import com.dabeeo.maps.vps.enums.VPSTrackingState
import com.dabeeo.maps.vps.model.Content2D
import com.dabeeo.maps.vps.model.Content3D
import com.dabeeo.maps.vps.model.ContentEvent
import com.dabeeo.maps.vps.views.VPSFragment
import com.eddiej.indoordemo.views.layout.ContentView
import com.google.ar.sceneform.animation.ModelAnimator
import java.util.*


/*
 * Copyright (c) 2020 DABEEO All rights reserved.
 *
 * This software is the confidential and proprietary information of DABEEO.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with DABEEO
 */


/**
 * Create by soyeongheo on 2020-03-11.
 * Description :
 */
class VPSActivity : AppCompatActivity() {

    private val mVPSFragment: VPSFragment by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_vps) as VPSFragment)
    }

    companion object {
        val TAG = VPSActivity::class.java.simpleName
    }

    private lateinit var mMapView: MapView
    private lateinit var mMyLocationMarker: Marker

    private val PERMISSIONS_REQUEST_CODE = 1000
    private var mMyLocation: Location? = null
    private var mIsMarkerAdd = false


    private val contentsEvent = object  : ContentEvent<Content3D>() {

        override fun error(content: Content3D?, message: String?) {
            Log.e(TAG,"${content!!.id} Render error")
        }

        override fun click(content: Content3D?) {
            Log.e(TAG,"${content!!.id} click event 발생")
            if(content.id == "helicopter" && !mHeliopterFirst) {
                val animatorSet = AnimatorSet()
                animatorSet.playTogether(
                    mVPSFragment.getContent3DMoveAnimator(content, content.location.x + 1000, content.location.y, 5000)
                    , ModelAnimator(content.animationData[0], content.modelRenderable).setDuration(5000)
                )
                animatorSet.start()
                mHeliopterFirst = true
            } else if(content.id == "andy") {
                ModelAnimator(content.animationData[0], content.modelRenderable).start()
            }
        }

        override fun distance(content: Content3D?, remainDistance: Double) {
        }
    }

    private val mHelicopter = Content3D("helicopter").apply {
        contentUri = Uri.parse("helicopter.sfb")
        setPosition(
            Location(
                3051.77769424003,
                1250.35618504251,
                1
            ), 2.0)
        scale = 0.5
        setDirection(90.0,DirectionType.FIXING)
        event = contentsEvent
    }
    private var mHeliopterFirst = false


    private val mAndy = Content3D("andy").apply {
        contentUri = Uri.parse("andy.sfb")
        setPosition(
            Location(
                3319.60906277984,
                1443.78890183607,
                1
            ), 0.0)
        scale = 2.0
        event = contentsEvent
    }

    private val mAvocado = Content3D("Avocado").apply {
        contentUri = Uri.parse("https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/Avocado/glTF/Avocado.gltf")
        setPosition(
            Location(
                2772.2763113399,
                1443.93351434154,
                1
            ), 1.0)
        scale = 5.0
    }



    /**
     * VPS Event
     */
    private val mVPSEvent = object  : VPSEvent() {
        override fun onLocation(location: Location?, direction: Double) {
            location?.let {
                val point = Point(it.x,it.y,0.0)
                if(mMyLocation == null) {
                    mMapView.setView(point,3.0,true)
                    if(mMapView.floor.level == it.floorLevel) {
                        mMyLocationMarker.addTo()
                    }
                }

                mMyLocationMarker.position = point       // 내 위치 마커 설정
                mMyLocation = location

                if(mIsMarkerAdd) {
                    mMapView.center = point
                }
            }
        }

        override fun onDirectionAngle(angle: Double) {
        }

        override fun onState(trackingState: VPSTrackingState?) {
            when(trackingState) {
                VPSTrackingState.PAUSED, VPSTrackingState.STOPPED -> {
                    val message = "위치를 잃어 버렸습니다."
                    Log.i(TAG,message)
                    toastError(message)
                    mMyLocationMarker.remove()
                }
                VPSTrackingState.WAITING -> {
                    val message = "위치 찾는 중..."
                    Log.i(TAG,message)
                    toastError(message)
                }
            }
        }

        override fun error(code: String?, message: String?) {
            Log.i(TAG,"VPS error code : $code , $message")
            toastError(message)
        }

        override fun ready() {
            Log.i(TAG,"VPS init SUCCESS")
            init2DContents()            // 2DContent init
            init3DContents()            // 3DContent init
        }

        override fun beginNetwork() {
            toastError("위치 탐색 시작")
        }

        override fun network(currentRetryCount: Int, totalRetryCount: Int, currentResult: Boolean) {
            Log.i(TAG,"위치 찾는 중... $currentRetryCount/$totalRetryCount : $currentResult")
        }

        override fun endNetwork(currentResult: Boolean, errorCode: String?, message: String?) {
            Log.i(TAG,"위치 결과... $message")

            if(currentResult) {
                toastError("위치를 찾았습니다.")
            } else {
                toastError("위치를 찾을수 없습니다.")
            }
        }
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            mMapView = mapView!!
            mMyLocationMarker = initMyLocationMarker()          // 내 위치 마커 init
            mMyLocationMarker.addTo()                           // 내 위치 마커 추가
        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }

        override fun floorEnd(floorInfo: FloorInfo?, reason: Reason?) {
            mMyLocation?.let {
                if(floorInfo!!.level == it.floorLevel) {        // 내 위치와 현재 층이 같을 경우 노출
                    mMyLocationMarker.addTo()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityVpsBinding>(this,R.layout.activity_vps).apply {
            if(isPermissionGranted()) {
                initVPSFragment()
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                    || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CODE
                    )

                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_CODE
                    )
                }
            }
        }
    }

    /**
     *
     */
    private fun initVPSFragment() {
        val mapOptions = object : MapOptions() {}.apply {
            minZoom = 2.0                       // 최소 줌 설정
            maxZoom = 10.0                      // 최대 줌 설정
            zoom = 3.0                          // 진입 줌 설정
            isUiFloorControls = true            // 층간 이동 UI 사용 설정
            isUiZoomControls = true             // 확대/축소 UI 사용 설정
        }

        val vpsOptions = object  : VPSOptions(applicationContext){}.apply {
            timeInterval = 100                  // 측위 interval 설정
        }
        mVPSFragment.init(vpsOptions,mapOptions,mVPSEvent, mMapEvent, MyApp.AUTHORIZATION)
    }

    /**
     * 권한 체크
     */
    private fun isPermissionGranted() : Boolean {
        return (checkSelfPermission(Manifest.permission.CAMERA)
                + checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) == PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if ((grantResults[0] + grantResults[1]) == PERMISSION_GRANTED) {
                    initVPSFragment()
                } else {
                    toastError("권한을 허용해 주세요.")
                    finish()
                }
                return
            }
        }
    }

    /**
     * init Marker
     */
    private fun initMyLocationMarker() : Marker {
        val markerOptions = MarkerOptions().apply {
            icon = Image(BitmapFactory.decodeResource(resources,R.drawable.img_mylocation)
            )
        }
        val marker = Marker(this,Point(0.0,0.0, 0.0), markerOptions, mMapView)
        marker.event = mMyMarkerEvent
        return marker
    }


    /**
     * MyLocationMarker Event
     */
    private val mMyMarkerEvent = object : DrawEvent<Marker> () {
        override fun add(drawObject: Marker?) {
            mIsMarkerAdd = true
        }

        override fun remove(isSuccess: Boolean, drawObject: Marker?) {
            mIsMarkerAdd = false
        }
    }


    /**
     * 2DContent init
     */
    private fun init2DContents() {
        val content2DList = ArrayList<Content2D>()
        val content2D = Content2D()
        val contentView = ContentView(applicationContext)
        contentView.setContentInfo(ContentInfo("휴게실","안락한 안마의자와 휴식 취해 해보아요.",
            "https://indoor.dabeeomaps.com/image/assets/logo_maps-3x-54848d261e3cc1fbd0465a25c50dcc7a.png"))
        content2D.view = contentView
        content2D.location =
            com.dabeeo.maps.basictype.Location(3060.887034100693, 1666.7951014514313, 1)
        content2DList.add(content2D)
        mVPSFragment.initContents2D(content2DList)
    }

    /**
     * 3DContent init
     */
    private fun init3DContents() {
        val content3DList = ArrayList<Content3D>()
        content3DList.add(mHelicopter)
        content3DList.add(mAndy)
        content3DList.add(mAvocado)
        mVPSFragment.initContents3D(content3DList)
    }


}