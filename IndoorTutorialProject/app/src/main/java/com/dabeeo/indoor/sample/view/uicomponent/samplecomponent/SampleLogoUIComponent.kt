package com.dabeeo.indoor.sample.view.uicomponent.samplecomponent

import android.content.Context
import android.view.LayoutInflater
import com.dabeeo.indoor.sample.databinding.UicomponentSampleLogoBinding
import com.dabeeo.maps.indoormap.ui.UIComponent

class SampleLogoUIComponent constructor(context: Context) : UIComponent(context) {

    init {
        addView(UicomponentSampleLogoBinding.inflate(LayoutInflater.from(context)).root)

        /**
         * UIComponent 영역 설정
         *
         * 미설정시 UIComponent의 충돌검사가 정상적으로 동작하지 않음
         */
        setWidthDp(240)
        setHeightDp(140)
    }
}