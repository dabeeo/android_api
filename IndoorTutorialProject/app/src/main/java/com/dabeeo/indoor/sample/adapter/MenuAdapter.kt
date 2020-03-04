package com.dabeeo.indoor.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dabeeo.indoor.sample.databinding.ItemMenuBinding
import com.dabeeo.indoor.sample.model.Menu

class MenuAdapter constructor(
    val mMenuList: List<Menu>,
    val mIMenuAdapterCallback: IMenuAdapterCallback
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    interface IMenuAdapterCallback {
        fun onClickItem(menu: Menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = mMenuList.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.onBind(mMenuList[position])
    }

    inner class MenuViewHolder constructor(val mBinding: ItemMenuBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.root.setOnClickListener { mIMenuAdapterCallback.onClickItem(mMenuList[adapterPosition]) }
        }

        fun onBind(menu: Menu) {
            mBinding.tvMenu.text = menu.title
        }
    }
}