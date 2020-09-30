package com.zoyo.mvvmkotlindemo.core.mvvm.widget.toast

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.zoyo.mvvmkotlindemo.R

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 */
class ToasterUtil {
    companion object {

        fun tint9PatchDrawableFrame(context: Context, @ColorInt tintColor: Int): Drawable? {
            val toastDrawable = getDrawable(context, R.mipmap.toast_frame) as NinePatchDrawable?
            return tintIcon(toastDrawable!!, tintColor)
        }

        fun tintIcon(drawable: Drawable, @ColorInt tintColor: Int): Drawable? {
            drawable.colorFilter = BlendModeColorFilter(tintColor, BlendMode.SRC_IN)
            return drawable
        }

        fun getDrawable(context: Context, @DrawableRes id: Int): Drawable? {
            return AppCompatResources.getDrawable(context, id)
        }

        /**
         * 设置背景图片
         *
         * @param view
         * @param drawable
         */
        fun setBackground(view: View, drawable: Drawable?) {
            view.background = drawable
        }

        fun getColor(context: Context?, @ColorRes color: Int): Int {
            return ContextCompat.getColor(context!!, color)
        }

    }
}