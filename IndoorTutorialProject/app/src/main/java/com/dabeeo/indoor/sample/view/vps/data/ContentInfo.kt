package com.dabeeo.indoor.sample.view.vps.data


/*
 * Copyright (c) 2020 DABEEO All rights reserved.
 *
 * This software is the confidential and proprietary information of DABEEO.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with DABEEO
 */


/**
 * Create by soyeongheo on 2020-03-12.
 * Description :
 */
class ContentInfo {

    constructor(title : String, content : String, url : String) {
        this.title = title
        this.content = content
        this.url = url
    }

    var title : String = ""
    var content : String = ""
    var url : String = ""
}