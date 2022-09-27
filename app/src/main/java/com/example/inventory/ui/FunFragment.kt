package com.example.inventory.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.inventory.R
import com.example.inventory.databinding.FragmentFunBinding


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

}