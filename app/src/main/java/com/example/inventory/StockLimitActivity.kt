package com.example.inventory


import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.inventory.databinding.ActivityStockLimitBinding
import com.example.inventory.spread.startActivity

class StockLimitActivity : BaseActivity() {
    private lateinit var mBinding:ActivityStockLimitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_stock_limit)

        mBinding.messageContainer1.setOnClickListener {
            startActivity<SettingsActivity> {
                putExtra("STOCK","1")
            }
        }
        mBinding.messageContainer2.setOnClickListener {
            startActivity<SettingsActivity> {
                putExtra("STOCK","2")
            }
        }
        mBinding.messageContainer3.setOnClickListener {
            startActivity<SettingsActivity> {
                putExtra("STOCK","3")
            }
        }
        mBinding.messageContainer4.setOnClickListener {
            startActivity<SettingsActivity> {
                putExtra("STOCK","4")
            }
        }
    }

}