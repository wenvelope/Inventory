package com.example.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.R
import com.example.inventory.databinding.FunctionItemBinding

class FunAdapter(val mList:List<String>): RecyclerView.Adapter<FunAdapter.ItemHolder>() {


    interface  FunItemOnClickListener{
        fun onClick(position: Int)
    }

    var onFunItemClickListener:FunItemOnClickListener ?=null


    inner class ItemHolder(private val mBinding:FunctionItemBinding):RecyclerView.ViewHolder(mBinding.root){
        fun bind(position: Int){
            mBinding.funName.text = mList[position]
            when(position){
                0->{
                    mBinding.funImage.setImageResource(R.drawable.ic_out_stock)
                }
                1->{
                    mBinding.funImage.setImageResource(R.drawable.ic_stock_select)
                }
                2->{
                    mBinding.funImage.setImageResource(R.drawable.ic_stock_batch)
                }
                3->{
                    mBinding.funImage.setImageResource(R.drawable.ic_alarm)
                }
                4->{

                }
                else->{
                    mBinding.funImage.setImageResource(R.drawable.ic_stock)
                }
            }
            mBinding.funContainer.setOnClickListener {
                onFunItemClickListener?.onClick(position)
            }
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