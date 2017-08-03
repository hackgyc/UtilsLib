package com.gyc.kotlinutils

import android.app.Activity
import java.util.*

/**
 * Created by Heather on 2017/7/25.
 */
object ActivityUtil{

    private val activityLinkedList = LinkedList<Activity>()

    // 向list中添加Activity
    fun addActivity(activity: Activity): ActivityUtil {
        activityLinkedList.add(activity)
        return this
    }

    // 结束特定的Activity(s)
    fun finshActivities(vararg activityClasses: Class<out Activity>): ActivityUtil {
        for (activity in activityLinkedList) {
            if (Arrays.asList(*activityClasses).contains(activity.javaClass)) {
                activity.finish()
            }
        }
        return this
    }

    // 结束所有的Activities
    fun finshAllActivities(): ActivityUtil {
        for (activity in activityLinkedList) {
            activity.finish()
        }
        return this
    }

}