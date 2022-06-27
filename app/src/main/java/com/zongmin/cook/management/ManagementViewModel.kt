package com.zongmin.cook.management

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Management
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ManagementViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {
    var _management = MutableLiveData<List<Management>>()

    val management: LiveData<List<Management>>
        get() = _management


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getManagementResult() {
//        Log.d("hank1","check1")

        coroutineScope.launch {
            val result = cookRepository.getManagement()

//            Log.d("hank1","check2")
            _management.value = when (result) {
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
//            Log.d("hank1","show result => ${result}")
//            Log.d("hank1","show recipes => ${recipes.value}")



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

    fun getThreeDay(): String {
        showTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime)) + " ~ " + SimpleDateFormat("yyyy/MM/dd").format(
            Date(showTime + (dayTime * 3))
        )
    }

    fun getWeek(): String {
        showTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime)) + " ~ " + SimpleDateFormat("yyyy/MM/dd").format(
            Date(showTime + (dayTime * 7))
        )
    }


}