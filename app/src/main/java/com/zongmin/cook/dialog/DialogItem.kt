package com.zongmin.cook.dialog

import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.PlanContent

sealed class DialogItem {

    data class Title(val title: String) : DialogItem() {
//        val id: Long = -1
    }

    data class FullPlan(val plan: Plan) : DialogItem() {
//        val id: Long = -1

    }


}