package com.example.inventory

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.inventory.databinding.ActivitySettingsBinding

class SettingsActivity : BaseActivity() {
    private lateinit var mBinding:ActivitySettingsBinding
    private lateinit var sp:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_settings)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        val extra = intent.getStringExtra("STOCK")

        mBinding.save.setOnClickListener {
            val up=mBinding.sizeUpEdit.text.toString()
            val down = mBinding.sizeDownEdit.text.toString()
            sp.edit().apply{
                extra?.let {
                    when(extra){
                        "1"->{
                            putString("仓库A_UP",up)
                            putString("仓库A_DOWN",down)
                        }
                        "2"->{
                            putString("仓库B_UP",up)
                            putString("仓库B_DOWN",down)
                        }
                        "3"->{
                            putString("仓库C_UP",up)
                            putString("仓库C_DOWN",down)
                        }
                        else->{
                            putString("仓库D_UP",up)
                            putString("仓库D_DOWN",down)
                        }
                    }
                    apply()
                }
            }
        }
    }





}