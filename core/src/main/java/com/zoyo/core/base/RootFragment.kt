package com.zoyo.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * zoyomng  2021/1/22
 */
abstract class RootFragment : Fragment(), IBaseView {

    lateinit var containerView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
       root:如果为null,布局参数(如layout_width,layout_height)不生效,默认为wrap_content
       * attachToRoot:true-将填充的布局添加到root布局中,false-不添加到root不居中
       使用场景:true-自定义控件时,需要将自定义的控件添加到父布局中
       false-有自己的添加逻辑(如fragment,FragmentManager.replace();
                           attachRoot为true时报错:此fragment对象已有父布局,需要先移除后添加)
       * */

        containerView = inflater.inflate(getLayoutId(), container, false)
        initialize()
        return containerView
    }


}