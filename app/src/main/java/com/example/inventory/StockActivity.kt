package com.example.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.adapter.MaterialAdapter
import com.example.inventory.databinding.ActivityStockBinding
import com.example.inventory.model.StockViewModel
import com.example.inventory.spread.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class StockActivity : BaseActivity() {
    private var uid by Delegates.notNull<Int>()
    private lateinit var mBinding: ActivityStockBinding
    private val  mModel:StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_stock)
        uid = intent.getIntExtra("uid",1)

        lifecycleScope.launch{
          val result =  mModel.selectMaterialListByRe(uid.toString()).getOrNull()
            mBinding.apply {
                stockDefaultRecyclerview.layoutManager = LinearLayoutManager(this@StockActivity)
                if(result!=null){
                    stockDefaultRecyclerview.adapter = MaterialAdapter(result.materialList)
                }else{
                    "网络错误".showToast()
                }
            }
        }
    }


}