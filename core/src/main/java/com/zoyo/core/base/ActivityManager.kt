package com.zoyo.core.base

import android.app.Activity
import java.util.*

class ActivityManager private constructor() {
    companion object {
        @JvmStatic
        val instance: ActivityManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    private val activityStack: Stack<Activity> = Stack()

    fun addActivity(activity: Activity) = activityStack.add(activity)
    fun currentActivity() = activityStack.lastElement()
    fun removeActivity(activity: Activity) = activityStack.remove(activity)
    fun finishActivity(activity: Activity) {
        if (!activity.isFinishing) {
            activity.finish()
            activityStack.remove(activity)
        }
    }

    fun finishActivity(clazz: Class<out Activity>) {
        for (activity in activityStack) {
            if (activity.javaClass == clazz) {
                finishActivity(activity)
            }
        }
    }

    fun finishAllActivity() {
        for (activity in activityStack) {
            finishActivity(activity)
        }
    }
}