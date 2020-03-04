package com.dabeeo.indoor.sample.view.drawobjects

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivityAddObjectsBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.maps.basictype.Image
import com.dabeeo.maps.basictype.Point
import com.dabeeo.maps.basictype.Size
import com.dabeeo.maps.draw.*
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.MapInfo
import java.net.URL
import java.text.SimpleDateFormat
import kotlin.random.Random

class AddDrawObjectsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddObjectsBinding

    private lateinit var mMapInfo: MapInfo
    private val mRandom = Random(1004)

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            mMapInfo = mapInfo!!
            mBinding.btnAddPolygon.setOnClickListener { addPolygon() }
            mBinding.btnAddPolyline.setOnClickListener { addPolyline() }
            mBinding.btnAddCircle.setOnClickListener { addCircle() }
            mBinding.btnAddMarker.setOnClickListener { addMarker() }
        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityAddObjectsBinding>(
            this,
            R.layout.activity_add_objects
        ).apply {
            mMapView.getMapSync(listOf(mMapEvent), AUTHORIZATION)
        }
    }

    /**
     * add Polygon
     * Polygon 생성자에 사용하는 포인트는 시계 방향 순서
     */
    private fun addPolygon() {
        val polygonOptions = PolygonOptions().apply {
            fillColor = getRandomColorHexCode()
            strokeColor = getRandomColorHexCode()
        }

        Polygon(
            listOf(
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width / 2),
                    mRandom.nextDouble(0.0, mMapInfo.size.height / 2),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(mMapInfo.size.width / 2, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height / 2),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(mMapInfo.size.width / 2, mMapInfo.size.width),
                    mRandom.nextDouble(mMapInfo.size.height / 2, mMapInfo.size.height),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width / 2),
                    mRandom.nextDouble(mMapInfo.size.height / 2, mMapInfo.size.height),
                    0.0
                )
            ),
            polygonOptions,
            mMapView
        ).addTo()
    }

    /**
     * add Polyline
     * Polyline 생성자에 사용하는 포인트는 라인의 포인트 순서
     */
    private fun addPolyline() {
        val polylineOptions = PolylineOptions().apply {
            color = getRandomColorHexCode()
        }

        Polyline(
            listOf(
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                ),
                Point(
                    mRandom.nextDouble(0.0, mMapInfo.size.width),
                    mRandom.nextDouble(0.0, mMapInfo.size.height),
                    0.0
                )
            ),
            polylineOptions,
            mMapView
        ).addTo()
    }

    /**
     * add Circle
     */
    private fun addCircle() {
        val circleOptions = CircleOptions().apply {
            radius = mRandom.nextDouble(50.0, 100.0)
            fillColor = getRandomColorHexCode()
            strokeColor = getRandomColorHexCode()
            fillOpacity = mRandom.nextDouble(0.0, 1.1)
            strokeOpacity = mRandom.nextDouble(0.0, 1.1)
        }

        Circle(
            Point(
                mRandom.nextDouble(0.0, mMapInfo.size.width),
                mRandom.nextDouble(0.0, mMapInfo.size.height),
                0.0
            ),
            circleOptions,
            mMapView
        ).addTo()
    }


    /**
     * add Marker
     * URL을 이용한 마커 추가
     */
    private fun addMarker() {
        val markerOptions = MarkerOptions().apply {
            icon = Image(
                Size(mRandom.nextDouble(50.0, 100.0), mRandom.nextDouble(50.0, 100.0)),
                URL("https://indoor.dabeeomaps.com/image/assets/logo_maps-3x-54848d261e3cc1fbd0465a25c50dcc7a.png")
            )
            title = SimpleDateFormat("hh:mm:ss").format(System.currentTimeMillis())
            fontColor = getRandomColorHexCode()
            fontSize = mRandom.nextInt(20, 40)
            isAutoRotate = mRandom.nextInt(2) == 0
            isAutoScale = mRandom.nextInt(4) == 2
        }

        Marker(
            this,
            Point(
                mRandom.nextDouble(0.0, mMapInfo.size.width),
                mRandom.nextDouble(0.0, mMapInfo.size.height),
                0.0
            ),
            markerOptions,
            mMapView
        ).addTo()
    }


    /**
     * for create color HexCode
     */
    private val mColors = listOf(
        "#39add1", // light blue
        "#3079ab", // dark blue
        "#c25975", // mauve
        "#e15258", // red
        "#f9845b", // orange
        "#838cc7", // lavender
        "#7d669e", // purple
        "#53bbb4", // aqua
        "#51b46d", // green
        "#e0ab18", // mustard
        "#637a91", // dark gray
        "#f092b0", // pink
        "#b7c0c7"  // light gray
    )

    private fun getRandomColorHexCode(): String {
        return mColors[mRandom.nextInt(mColors.size)]
    }
}