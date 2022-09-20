package com.example.inventory

import android.app.Activity
import java.util.*

class AppManager {

    companion object{
        private val activityStack = Stack<Activity>()

        fun addActivity(activity: Activity){
            activityStack.push(activity)
        }

        fun getCurrentActivity(): Activity?{
            return if(activityStack.size>=1){
                activityStack.lastElement()
            }else{
                null
            }
        }

        fun finishCurrentActivity(){
            if(activityStack.size>=1){
                val activity = activityStack.pop()
                activity.finish()
            }
        }

        fun finishActivity(activity: Activity){
            activityStack.remove(activity)
            if (!activity.isFinishing){
                activity.finish()
            }
        }

        fun finishActivity(cls: Class<*>) {
            for (activity in activityStack) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        }

        fun finishAllActivity(){
            activityStack.forEach {
                it?.finish()
            }
            activityStack.clear()
        }




    }

}