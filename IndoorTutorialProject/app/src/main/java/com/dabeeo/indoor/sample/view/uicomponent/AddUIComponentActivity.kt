package com.dabeeo.indoor.sample.view.uicomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.MyApp.Companion.AUTHORIZATION
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.databinding.ActivityAddUicomponentBinding
import com.dabeeo.indoor.sample.toastError
import com.dabeeo.indoor.sample.view.uicomponent.samplecomponent.SampleLogoUIComponent
import com.dabeeo.maps.event.MapEvent
import com.dabeeo.maps.indoormap.MapView
import com.dabeeo.maps.indoormap.data.MapInfo
import com.dabeeo.maps.indoormap.ui.UIPositionType

class AddUIComponentActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddUicomponentBinding

    private val mSampleLogoUIComponent: SampleLogoUIComponent by lazy {
        SampleLogoUIComponent(this)
    }

    private val mMapView: MapView by lazy {
        (supportFragmentManager.findFragmentById(R.id.fragment_mapview) as MapView)
    }

    private val mMapEvent = object : MapEvent() {
        override fun ready(mapView: MapView?, mapInfo: MapInfo?) {
            mBinding.btnAddUicomponent.setOnClickListener {
                mMapView.addUIComponent(
                    mSampleLogoUIComponent,
                    0,
                    UIPositionType.TOP_LEFT
                )
            }
            mBinding.btnRemoveUicomponent.setOnClickListener {
                mMapView.removeUIComponent(
                    mSampleLogoUIComponent
                )
            }
            mBinding.btnExtendView.setOnClickListener { extendMapView() }
            mBinding.btnReduceView.setOnClickListener { reduceMapView() }
        }

        override fun error(code: String?, message: String?) {
            toastError(message)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityAddUicomponentBinding>(
            this,
            R.layout.activity_add_uicomponent
        ).apply {
            mMapView.getMapSync(listOf(mMapEvent), AUTHORIZATION)
        }
    }

    private fun extendMapView() {
        val lp = mBinding.clParent.layoutParams.apply {
            width = mBinding.clParent.width + 20
            height = mBinding.clParent.height + 20
        }
        mBinding.clParent.layoutParams = lp
    }

    private fun reduceMapView() {
        val lp = mBinding.clParent.layoutParams.apply {
            width = mBinding.clParent.width - 20
            height = mBinding.clParent.height - 20
        }
        mBinding.clParent.layoutParams = lp
    }
}