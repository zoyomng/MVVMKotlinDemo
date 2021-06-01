package com.zoyo.mvvmkotlindemo.ui.skin

import android.graphics.drawable.Drawable
import android.text.TextUtils
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.zoyo.core.BaseApplication
import com.zoyo.core.base.BaseViewModel
import com.zoyo.mvvmkotlindemo.R

class SkinViewModel : BaseViewModel() {

    var count = MutableLiveData(0)
    var drawable = MutableLiveData<Drawable>()

    init {
        drawable.value =
            ContextCompat.getDrawable(BaseApplication.getContext(), R.drawable.ic_launcher)
    }

    fun refreshSkin() {
        count.value = count.value?.plus(1)
        if (count.value!! % 2 == 0) {
            getDrawable(R.drawable.ic_launcher, "round")
        } else {
            getDrawable(R.drawable.avatar, "")
        }
    }

    private fun getDrawable(resId: Int, skinName: String) {
        drawable.value =
            ContextCompat.getDrawable(BaseApplication.getContext(), getResId(resId, skinName))
    }

    private fun getResId(resId: Int, skinName: String): Int {
        val resourceEntryName = getResourceEntryName(resId, skinName)

        val resourceTypeName = BaseApplication.getContext().resources.getResourceTypeName(resId)
        return BaseApplication.getContext().resources.getIdentifier(
            resourceEntryName,
            resourceTypeName,
            BaseApplication.getContext().packageName
        )
    }

    private fun getResourceEntryName(resId: Int, skinName: String): String? {
        if (TextUtils.isEmpty(skinName)) {
            return BaseApplication.getContext().resources.getResourceEntryName(resId)
        }

        return "${BaseApplication.getContext().resources.getResourceEntryName(resId)}_${skinName}"
    }




}