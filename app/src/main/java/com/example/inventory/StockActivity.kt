package com.example.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.inventory.databinding.ActivityStockBinding
import kotlin.properties.Delegates

class StockActivity : BaseActivity() {
    private var uid by Delegates.notNull<Int>()
    private lateinit var mBinding: ActivityStockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_stock)
        uid = intent.getIntExtra("uid",1)
        mBinding.stockName.text = uid.toString()
    }
}