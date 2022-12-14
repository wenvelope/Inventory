package com.example.inventory

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.adapter.MaterialAdapter
import com.example.inventory.databinding.ActivityOutStockBinding
import com.example.inventory.model.StockOutViewModel
import com.example.inventory.repository.Repository
import com.example.inventory.room.Material
import com.example.inventory.spread.showToast
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutStockActivity : BaseActivity() {
    private lateinit var mBinding:ActivityOutStockBinding
    private val mModel:StockOutViewModel by viewModels()
    private lateinit var sp:SharedPreferences
    private lateinit var handler: Handler

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_out_stock)

        handler = Handler(mainLooper)
        mBinding.outButton.setOnClickListener {
            scan()
        }
        mBinding.outRecyclerview.layoutManager = LinearLayoutManager(this)
        mBinding.outRecyclerview.adapter = MaterialAdapter(mModel.outMaterialList)

        mModel.outMaterial.observe(this){
            mModel.outMaterialList.clear()
            mModel.outMaterialList.addAll(it)
            mBinding.outRecyclerview.adapter?.notifyDataSetChanged()
        }

        mModel.selectOutMaterial()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= RESULT_OK || data==null){
            return
        }
        when(requestCode){
            200->{
                val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT) // ?????????????????? ScanUtil.RESULT
                if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                    val char = hmsScan?.getOriginalValue()?.split(" ")
                    char?.let {
                        val area = sp.getString("AREA","0")
                        if(char[char.size-1]=="weifangzhou"&&char.size==7){
                            val temp = Material(char[0],char[1],"0",char[2],area!!,char[3],char[4],char[5])
                            lifecycleScope.launch(Dispatchers.IO){
                                val end =  Repository.takeOutById(char[0],area)
                                if(end){
                                    if(!mModel.insertMaterial(temp)){
                                        mModel.takeLocalOutById(char[0],area)
                                    }
                                    handler.post { "????????????".showToast() }
                                }else{
                                    handler.post { "????????????????????????????????????".showToast()  }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    private fun scan(){
        val option = HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
        PermissionX.init(this)
            .permissions(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .request{ allgranted,grantedlist,deniedlist->
                if (!allgranted){
                    Toast.makeText(MyApplication.context,"????????????????????????", Toast.LENGTH_SHORT).show()
                }else{
                    ScanUtil.startScan(
                        this,200,option
                    )
                }
            }
    }
}