package com.eddiej.indoordemo.views.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dabeeo.indoor.sample.databinding.ViewContentBinding
import com.dabeeo.indoor.sample.view.vps.data.ContentInfo
import com.squareup.picasso.Picasso


/*
 * Copyright (c) 2019 DABEEO All rights reserved.
 *
 * This software is the confidential and proprietary information of DABEEO.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with DABEEO
 */



/**
 * Create by soyeongheo on 2019-12-17.
 * Description :
 */
class ContentView : ConstraintLayout {
    private lateinit var binding: ViewContentBinding
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }


    private fun init(context: Context?) {
        binding = ViewContentBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
    }

    public fun setContentInfo(infoData : ContentInfo) {
        binding.contentTextView.text = infoData.content
        binding.nameTextView.text = infoData.title
        Picasso.get().load(infoData.url).into(binding.contentImageView)
    }

}
