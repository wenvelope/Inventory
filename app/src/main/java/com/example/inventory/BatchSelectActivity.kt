package com.example.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.adapter.MaterialAdapter
import com.example.inventory.databinding.ActivityBatchSelectBinding
import com.example.inventory.model.BatchSelectModel

class BatchSelectActivity : BaseActivity() {
    private lateinit var mBinding:ActivityBatchSelectBinding
    private val mModel:BatchSelectModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_batch_select)

        mModel.materialInBoundNum.observe(this){
            if (it.isNotEmpty()){
                mModel.materialInBoundNumList.clear()
                mModel.materialInBoundNumList.addAll(it)
                mBinding.batchRecyclerView.adapter?.notifyDataSetChanged()
            }

        }

        mBinding.batchRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.batchRecyclerView.adapter = MaterialAdapter(mModel.materialInBoundNumList)

        mBinding.numEditText.addTextChangedListener{
            mModel.selectAllByInBoundNum(it.toString())
        }

    }



}