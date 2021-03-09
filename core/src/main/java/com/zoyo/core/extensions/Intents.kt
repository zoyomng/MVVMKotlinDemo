package com.zoyo.core.extensions

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File

/**
 * 2021/1/22       97440
 * 内联函数:在编译期,将函数方法体注入到调用这个函数的地方
 * 优缺点:代码量增多,性能提升,减少方法压栈出栈的资源消耗,对代码可读性不会造成影响
 * 关键字段:inline noinline crossinline
 * reified:具体化
 * 具体化泛型,java中是不允许使用泛型的类型的(如T.class)
 */


/**
 * 使用方法:startActivity<MainActivity>()
 */
inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Service> Context.startService() {
    startService(Intent(this, T::class.java))
}

/**
 * 跳转到拨号界面
 */
fun Context.jumpCall(number: String) {
    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
}

/**
 * 直接拨打电话
 * 需要加上权限 <uses-permission id="android.permission.CALL_PHONE" />
 */
fun Context.makeCall(number: String) {
    startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$number")))
}

/**
 * 发送短信
 */
fun Context.sms(number: String, text: String) {
    startActivity(
        Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number"))
            .apply {
                putExtra("sms_body", text)
            }
    )
}

/**
 * 打开浏览器
 */
fun Context.browser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * 发送邮件
 */
fun Context.email(email: String, subject: String, text: String) {
    startActivity(
        Intent(Intent.ACTION_SENDTO)
            .apply {
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, text)
                setType("text/plain")
            }
    )
}

/**
 *地图定位
 */
//fun Context.map() {
//    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:$")))
//}

/**
 * 播放音频
 */
fun Context.playAudio(file: String) {
    startActivity(Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(Uri.parse(file), "audio/mp3")
    })
}

/**
 * 选择(剪切)照片
 */
fun Activity.pickPicture(
    requestCode: Int,
    crop: Boolean = false,
    aspectX: Int = 1,
    aspectY: Int = 1,
    outputX: Int = 1,
    outputY: Int = 1,
    output: String = Environment.getStorageDirectory().path,
    outputFormat: String = "JPEG"
) {
    startActivityForResult(
        Intent(Intent.ACTION_GET_CONTENT)
            .apply {
                setType("image/*")
                if (crop) {
                    putExtra("crop", crop)
                    putExtra("aspectX", aspectX); // 剪切的宽高比为1：2
                    putExtra("aspectY", aspectY);
                    putExtra("outputX", outputX); // 保存图片的宽和高
                    putExtra("outputY", outputY);
                    putExtra("output", Uri.fromFile(File(output))); // 保存路径
                    putExtra("outputFormat", outputFormat);// 返回格式
                }
            }, requestCode
    )
}

/**
 * 剪切指定图片
 */
fun Activity.cropPicture(
    requestCode: Int,
    originFile: File,
    crop: Boolean = false,
    aspectX: Int = 1,
    aspectY: Int = 1,
    outputX: Int = 1,
    outputY: Int = 1,
    output: String = Environment.getStorageDirectory().path,
    outputFormat: String = "JPEG"
) {
    startActivityForResult(
        Intent("com.android.camera.action.CROP")
            .apply {
                setType("image/*")
                setClassName("com.android.camera", "com.android.camera.CropImage")
                data = Uri.fromFile(originFile);
                if (crop) {
                    putExtra("crop", crop)
                    putExtra("aspectX", aspectX) // 剪切的宽高比为1：2
                    putExtra("aspectY", aspectY)
                    putExtra("outputX", outputX)// 保存图片的宽和高
                    putExtra("outputY", outputY)
                    putExtra("scale", true)
                    putExtra("noFaceDetection", true)
                    putExtra("output", Uri.fromFile(File(output))); // 保存路径
                    putExtra("outputFormat", outputFormat);// 返回格式
                }
            }, requestCode
    )
}


/**
 * 拍照
 * 取出照片数据
 * val extras:Bundle = intent.extras
 * val bitmap:Bitmap = extras.get("data")
 */
fun Activity.takePicture(requestCode: Int) {
    startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), requestCode)
}

/**
 * 打开手机应用商店
 */
fun Context.appMarket(packageName: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
}

/**
 * 安装应用
 */
fun Context.install(file: File) {
    startActivity(Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
    })
}

fun Context.unInstall(packageName: String) {
    startActivity(Intent(Intent.ACTION_DELETE, Uri.parse("package:$packageName")))
}

/**
 * 进入设置页面
 */
fun Context.setting() {
    startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
}

fun Context.openWirelessSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

/**
 * 退回到桌面
 */
fun Context.startHomeActivity() {
    val homeIntent = Intent(Intent.ACTION_MAIN)
    homeIntent.addCategory(Intent.CATEGORY_HOME)
    startActivity(homeIntent)
}










