package com.zoyo.mvvmkotlindemo.core.widget.toast

import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.zoyo.mvvmkotlindemo.App
import com.zoyo.mvvmkotlindemo.R

/**
 * Copyright (c) dtelec, Inc All Rights Reserved.
 *
 * @Description: Toast的封装类
 * 解决一下问题:
 * 1.多次连续点击,多个吐司会一一显示消失,用户体验不好
 * 2.多次连续点击,会有一段时间不显示吐司
 * 3.自定义吐司
 * Application中初始化配置(可不初始化)
 * Toaster.Configs.getInstance()
 *       .isTintIcon(true)
 *       .setTextSize(16)
 *       .setGravity(Gravity.CENTER, 0, 0)
 *       .apply();
 *
 */
object Toaster {
    private val DEFAULT_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    private const val DEFAULT_GRAVITY = Gravity.CENTER
    private var currentTypeface = DEFAULT_TYPEFACE

    //  是否渲染图标
    private var isTintIcon = true
    private var textSize = 16
    private var currentGravity = DEFAULT_GRAVITY
    private var currentXOffset = 0
    private var currentYOffset = 0
    private var lastToast: Toast? = null

    /**
     * @param text
     */

    fun normal(text: CharSequence): Toast {
        return build(
            text = text,
            textColor = R.color.normalColor,
        )
    }

    fun info(text: CharSequence): Toast {
        return build(
            text = text,
            icon = R.drawable.ic_toast_info_white_24dp,
            textColor = R.color.infoColor,
        )
    }

    fun warning(text: CharSequence): Toast {
        return build(
            text = text,
            icon = R.drawable.ic_toast_warning_orange_24dp,
            textColor = R.color.warningColor,
        )
    }


    fun success(text: CharSequence): Toast {
        return build(
            text = text,
            icon = R.drawable.ic_toast_success_green_24dp,
            textColor = R.color.successColor,
        )
    }

    fun error(text: CharSequence): Toast {
        return build(
            text = text,
            icon = R.drawable.ic_toast_error_red_24dp,
            textColor = R.color.errorColor,
        )
    }


    /**
     * @param context             上下文
     * @param text                文本
     * @param icon                图标
     * @param tintColor           渲染色值
     * @param textColor           文字颜色
     * @param duration            时长
     * @param isTintBackgroundRes 背景图片是否渲染
     * @return Toast对象
     */
    @CheckResult
    private fun build(
        text: CharSequence,
        @ColorRes textColor: Int = R.color.defaultTextColor,
        @DrawableRes icon: Int? = null,
    ): Toast {

        val currentToast: Toast = Toast(App.appContext)

        val contentView = LayoutInflater.from(App.appContext).inflate(R.layout.toast_layout, null)

        val toastText = contentView.findViewById<TextView>(R.id.text)
        toastText.text = text
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            toastText.setTextColor(
                App.appContext.resources.getColor(
                    textColor,
                    App.appContext.theme
                )
            )
        }
        toastText.setTypeface(currentTypeface)
        toastText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

        icon?.let {
            val toastIcon = contentView.findViewById<ImageView>(R.id.icon)
            toastIcon.setImageResource(icon)
        }

        currentToast.view = contentView
        currentToast.setGravity(currentGravity, currentXOffset, currentYOffset)

        //解决问题1.多次连续点击,多个吐司会一一显示消失,用户体验不好
        lastToast?.cancel()

        lastToast = currentToast
        return currentToast
    }

    /**
     * 配置参数
     */
    class Config private constructor() {
        private var typeface = currentTypeface
        private var textSize = Toaster.textSize
        private var isTintIcon = Toaster.isTintIcon
        private var mGravity = currentGravity
        private var mX = currentXOffset
        private var mY = currentYOffset

        @CheckResult
        fun setTypeface(typeface: Typeface): Config {
            this.typeface = typeface
            return this
        }

        @CheckResult
        fun setTextSize(textSize: Int): Config {
            this.textSize = textSize
            return this
        }

        @CheckResult
        fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): Config {
            mGravity = gravity
            mX = xOffset
            mY = yOffset
            return this
        }

        @CheckResult
        fun isTintIcon(isTintIcon: Boolean): Config {
            this.isTintIcon = isTintIcon
            return this
        }

        fun apply() {
            currentTypeface = typeface
            Toaster.isTintIcon = isTintIcon
            Toaster.textSize = textSize
            currentGravity = mGravity
            currentXOffset = mX
            currentYOffset = mY
        }

        companion object {
            @get:CheckResult
            val instance: Config
                get() = Config()

            fun reset() {
                currentTypeface = DEFAULT_TYPEFACE
                textSize = 16
                isTintIcon = true
                currentGravity = DEFAULT_GRAVITY
                currentXOffset = 0
                currentYOffset = 0
            }
        }
    }
}
