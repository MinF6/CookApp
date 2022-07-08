package com.zongmin.cook.plan

import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.PlanContent

sealed class PlanItem {

    data class Title(val title: String) : PlanItem () {
        val id: Long = -1
    }

    data class FullPlan(val plan: Plan) : PlanItem () {
        val id: Long = -1

    }

}