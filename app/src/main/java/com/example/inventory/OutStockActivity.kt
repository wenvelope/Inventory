package com.example.inventory

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.databinding.ActivityOutStockBinding
import com.example.inventory.model.StockOutViewModel
import com.example.inventory.room.Material
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_out_stock)
        mBinding.outButton.setOnClickListener {
            scan()
        }
        mBinding.outRecyclerview.layoutManager = LinearLayoutManager(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= RESULT_OK || data==null){
            return
        }
        when(requestCode){
            200->{
                val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT) // 获取扫码结果 ScanUtil.RESULT
                if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                    val char = hmsScan?.getOriginalValue()?.split(" ")
                    char?.let {
                        val area = sp.getString("AREA","0")
                        if(char[char.size-1]=="weifangzhou"&&char.size==7){
                            val temp = Material(char[0],char[1],"0",char[2],area!!,char[3],char[4],char[5])
                            lifecycleScope.launch(Dispatchers.IO){
                                if(mModel.insertMaterial(temp)){
                                    // TODO: 出库逻辑
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
                    Toast.makeText(MyApplication.context,"部分功能无法使用", Toast.LENGTH_SHORT).show()
                }else{
                    ScanUtil.startScan(
                        this,200,option
                    )
                }
            }
    }
}