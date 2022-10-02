package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.R
import com.example.inventory.databinding.FunctionItemBinding

class FunAdapter(val mList:List<String>): RecyclerView.Adapter<FunAdapter.ItemHolder>() {
    inner class ItemHolder(private val mBinding:FunctionItemBinding):RecyclerView.ViewHolder(mBinding.root){
        fun bind(position: Int){
            mBinding.funName.text = mList[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val mBinding: FunctionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.function_item,
            parent,
            false
        )
        return ItemHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mList.size
}