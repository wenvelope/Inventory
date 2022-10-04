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
import com.example.inventory.databinding.ActivityCheckBinding
import com.example.inventory.model.CheckViewModel
import com.example.inventory.repository.Repository
import com.example.inventory.room.Material
import com.example.inventory.spread.showToast
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCheckBinding
    private val  mModel: CheckViewModel by viewModels()
    private var isStarted = false
    private lateinit var sp:SharedPreferences
    private lateinit var handler:Handler

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_check)
        sp = getSharedPreferences("TOKEN",Context.MODE_PRIVATE)
        mBinding.scanButton.isEnabled  = false
        handler = Handler(mainLooper)
        mBinding.endButton.setOnClickListener {
            isStarted = false
            mBinding.scanButton.isEnabled = false
        }
        mBinding.startButton.setOnClickListener {
                isStarted = true
                mBinding.scanButton.isEnabled = true
        }
        mBinding.scanButton.setOnClickListener {
            scan()
        }

        mBinding.list.layoutManager = LinearLayoutManager(this)
        mBinding.list.adapter = MaterialAdapter(mModel.materialList)

        val area = sp.getString("AREA","0")

        lifecycleScope.launch(Dispatchers.IO){
            if(area!="0"){
                val materialListByRe = mModel.selectMaterialListByRe(area!!).getOrNull()
                materialListByRe?.let {
                    mModel.materialList.clear()
                    mModel.materialList.addAll(it.materialList)
                    mBinding.list.post {
                        mBinding.list.adapter?.notifyDataSetChanged()
                    }
                    mModel.maxSize.postValue(mModel.materialList.size.toString())
                }
            }
        }

        mModel.maxSize.observe(this){
            val s = it.toInt()-mModel.materialList.size
            mBinding.detail.text = "$s/$it"
        }


    }


    @SuppressLint("NotifyDataSetChanged")
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
                            val temp = Material(char[0],char[1],"1",char[2],area!!,char[3],char[4],char[5])
                            lifecycleScope.launch{
                                if(mModel.materialList.remove(temp)){
                                    mModel.maxSize.value = mModel.maxSize.value
                                    mBinding.list.adapter?.notifyDataSetChanged()
                                    "成功".showToast()
                                }else{
                                    "已出库或者不在本仓库内".showToast()
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