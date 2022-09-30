package com.example.inventory

import android.app.Activity
import com.example.inventory.spread.showToast
import java.util.*
import kotlin.reflect.KClass

class AppManager {

    companion object{
        private val activityStack = LinkedList<Activity>()

        fun addActivity(activity: Activity){
            activityStack.push(activity)
        }



        fun finishOneActivity(activityName: String) {
            //在activities集合中找到类名与指定类名相同的Activity就关闭
            for (activity in activityStack) {
                val name = activity.javaClass.name//activity的类名
                if (name == activityName) {
                    if(activity.isFinishing){
                        activityStack.remove(activity)
                    }else{
                        activity.finish()
                    }
                }
            }
        }


        fun finishOtherActivity(activityName: String) {
            for (activity in activityStack) {
                val name = activity.javaClass.name //activity的类名
                if (name != activityName) {
                    if(activity.isFinishing){
                        activityStack.remove(activity)
                    }else{
                        activity.finish()
                    }
                }
            }
        }

        fun finishAll() {
            for (activity in activityStack) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            activityStack.clear()
        }



        fun removeActivity(activity: Activity){
            activityStack.remove(activity)
        }




    }

}