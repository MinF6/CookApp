package com.zongmin.cook.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DialogPlanViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    var _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan


    init {
//        getPlanResult()
        Log.d("hank1", "進到DialogPlanViewModel")
    }

    fun getPlanResult() {
        coroutineScope.launch {
            val result = cookRepository.getPlan()
//            Log.d("hank1","show result => ${result}")
            _plan.value = when (result) {
                is Result.Success -> {
                    result.data
                }
                is Result.Fail -> {
                    null
                }
                is Result.Error -> {

                    null
                }
                else -> {

                    null
                }
            }
            Log.d("hank1", "show recipes => ${plan.value}")
        }
    }

    var showTime = System.currentTimeMillis()
    val dayTime = 24 * 60 * 60 * 1000L

    //可考慮加星期幾
    fun getToday(): String {
        showTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))

    }

    fun getYesterday(): String? {
        showTime -= dayTime
        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
    }

    fun getTomorrow(): String? {
        showTime += dayTime
        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
    }

}