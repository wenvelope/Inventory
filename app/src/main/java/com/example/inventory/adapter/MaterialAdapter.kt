package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.R
import com.example.inventory.bean.MaterialBean
import com.example.inventory.databinding.MaterialItemBinding
import com.example.inventory.room.Material

class MaterialAdapter(val materialList: List<Material>):RecyclerView.Adapter<MaterialAdapter.MaterialHolder>() {
    inner class MaterialHolder(val mBinding:MaterialItemBinding): RecyclerView.ViewHolder(mBinding.root) {
        fun bind(position: Int){
            mBinding.apply {
                materialName.text = materialList[position].name
                materialUid.text = materialList[position].uid
                materialState.text = if(materialList[position].state=="1") "已入库" else "未入库"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialHolder {
        val mBinding:MaterialItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.material_item,parent,false)
        return MaterialHolder(mBinding)
    }

    override fun onBindViewHolder(holder: MaterialHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return materialList.size
    }
}