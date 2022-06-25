package com.zongmin.cook.plan

import com.zongmin.cook.data.PlanContent

sealed class PlanItem {

    data class Title(val title: String) : PlanItem () {
        val id: Long = -1
    }

    data class FullPlan(val planContent: PlanContent) : PlanItem () {
        val id: Long = -1

    }

}