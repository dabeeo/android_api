package com.dabeeo.indoor.sample.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dabeeo.indoor.sample.R
import com.dabeeo.indoor.sample.adapter.MenuAdapter
import com.dabeeo.indoor.sample.databinding.ActivityMainBinding
import com.dabeeo.indoor.sample.model.Menu
import com.dabeeo.indoor.sample.view.animation.MapAnimationActivity
import com.dabeeo.indoor.sample.view.basic.SimpleViewerActivity
import com.dabeeo.indoor.sample.view.drawobjects.AddDrawObjectsActivity
import com.dabeeo.indoor.sample.view.event.DrawObjectsEventActivity
import com.dabeeo.indoor.sample.view.event.PoiEventActivity
import com.dabeeo.indoor.sample.view.navigation.PreviewActivity
import com.dabeeo.indoor.sample.view.uicomponent.AddUIComponentActivity

class MainActivity : AppCompatActivity() {

    private val mMenuList = listOf(
        Menu(SimpleViewerActivity::class.java, "1. Basic MapView"),
        Menu(AddDrawObjectsActivity::class.java, "2. Add DrawObjects"),
        Menu(AddUIComponentActivity::class.java, "3. Add UIComponent"),
        Menu(PoiEventActivity::class.java, "4. Add Event (Map Poi)"),
        Menu(DrawObjectsEventActivity::class.java, "5. Add Event (Custom DrawObjects)"),
        Menu(MapAnimationActivity::class.java, "6. Map Animation"),
        Menu(PreviewActivity::class.java, "7. Preview")
    )

    private val mAdapterCallback = object : MenuAdapter.IMenuAdapterCallback {
        override fun onClickItem(menu: Menu) {
            startActivity(Intent(this@MainActivity, menu.menuClass))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            rvMenu.adapter = MenuAdapter(mMenuList, mAdapterCallback)
        }
    }
}
