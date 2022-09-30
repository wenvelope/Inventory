package com.example.inventory.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.MyApplication
import com.example.inventory.R
import com.example.inventory.adapter.MaterialAdapter
import com.example.inventory.databinding.FragmentScanBinding
import com.example.inventory.model.HomeViewModel
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.permissionx.guolindev.PermissionX


class ScanFragment : Fragment() {
    private lateinit var mBinding:FragmentScanBinding
    private lateinit var fatherInstance:HomeActivity
    val mModel:HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fatherInstance = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_scan,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    private fun scan(){
        val option = HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
        PermissionX.init(fatherInstance)
            .permissions(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .request{ allgranted,grantedlist,deniedlist->
                if (!allgranted){
                    Toast.makeText(MyApplication.context,"部分功能无法使用", Toast.LENGTH_SHORT).show()
                }else{
                    ScanUtil.startScan(
                        fatherInstance,200,option
                    )
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView(){
        mBinding.scanBg.setOnClickListener {
            scan()
        }
        mBinding.scanButton.setOnClickListener {
            scan()
        }
        mBinding.MaterialRecyclerView.layoutManager = LinearLayoutManager(fatherInstance)
        mBinding.MaterialRecyclerView.adapter = MaterialAdapter(mModel.materialList)

        activity?.apply {
            mModel.materialData.observe(this){
                mModel.materialList.clear()
                mModel.materialList.addAll(it)
                mBinding.MaterialRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        mModel.getAllLocalMaterial()
    }




}