package com.example.inventory.ui

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.OutStockActivity
import com.example.inventory.R
import com.example.inventory.StockActivity
import com.example.inventory.adapter.FunAdapter
import com.example.inventory.adapter.FunItemDecoration
import com.example.inventory.databinding.FragmentFunBinding
import com.example.inventory.model.StockOutViewModel
import com.example.inventory.spread.showToast
import com.example.inventory.spread.startActivity


class FunFragment : Fragment() {
    private lateinit var mBinding:FragmentFunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_fun,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.apply {
            val funList = mutableListOf<String>().apply {
                add("出库管理")
                add("盘点管理")
                add("批次查询")
                add("库存限制")
                add("虚仓管理")
                add("仓库A")
                add("仓库B")
                add("仓库C")
                add("仓库D")
            }
            mBinding.funRecyclerView.layoutManager = GridLayoutManager(this,6).also {
                it.spanSizeLookup = object :GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return if(position<=4){
                            2
                        }else{
                            3
                        }
                    }
                }
            }
            mBinding.funRecyclerView.addItemDecoration(FunItemDecoration())
            mBinding.funRecyclerView.adapter = FunAdapter(funList).also {
                it.onFunItemClickListener = object:FunAdapter.FunItemOnClickListener{
                    override fun onClick(position: Int) {
                        when(position){
                            0->{
                                startActivity<OutStockActivity> { }
                            }
                            else->{
                                startActivity<StockActivity> {
                                    putExtra("uid",position-4)
                                }
                            }
                        }

                    }

                }
            }
        }

    }

}