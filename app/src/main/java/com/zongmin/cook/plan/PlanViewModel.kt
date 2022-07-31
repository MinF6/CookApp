package com.zongmin.cook.plan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Management
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PlanViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan

    private var _deletePlanResult = MutableLiveData<String>()

    val deletePlanResult: LiveData<String>
        get() = _deletePlanResult

    private var _management = MutableLiveData<List<Management>>()

    val management: LiveData<List<Management>>
        get() = _management

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _pf = MutableLiveData<Boolean>()

    val pf: LiveData<Boolean>
        get() = _pf

    private val _toDay = MutableLiveData<Long>()

    val toDay: LiveData<Long>
        get() = _toDay

//    var saveTime: Long = 0L

    private val _time = MutableLiveData<Long>()

    val time: LiveData<Long>
        get() = _time



    init {
        setDate()
//        _pf.value =
//        getPlanResult()
//        Log.d("hank1","進到PlanViewModel")
    }

    private fun setDate() {
        val toDay = System.currentTimeMillis()
        val year = SimpleDateFormat("yyyy").format(Date(toDay)).toInt()
        val month = SimpleDateFormat("MM").format(Date(toDay)).toInt()
        val day = SimpleDateFormat("dd").format(Date(toDay)).toInt()

//        Log.d("hank1", "顯示今天$year 年$month 月 $day 日")


//        val storedDate = GregorianCalendar(2022, 6, 17)
        val storedDate = GregorianCalendar(year, month - 1, day)
//        Log.d("hank1", "今天轉換成毫秒是 -> ${storedDate.timeInMillis}")
        _toDay.value = storedDate.timeInMillis

//        Log.d(
//            "hank1",
//            "毫秒轉轉日期是 -> ${SimpleDateFormat("yyyy/MM/dd").format(Date(storedDate.timeInMillis))}"
//        )
    }

    fun getPlanResult(userId: String, time: Long) {

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
//            Log.d("hank1","狀態 -> ${status.value}")
            _pf.value = true
//            Log.d("hank1","我丟的時間是 -> ${time}")

            val result = cookRepository.getPlan(userId, time)
//            Log.d("hank1","show result => ${result}")
            _plan.value = when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
                    _pf.value = false
                    _time.value = time

                    result.data
                }
                is Result.Fail -> {
                    Log.d("hank1","失敗1")
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    Log.d("hank1","失敗2")
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    Log.d("hank1","失敗3")
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
//            Log.d("hank1","show result => ${result}")
            Log.d("hank1","show recipes => ${plan.value}")
        }
    }

    fun deletePlan(id: String, time: Long) {
        coroutineScope.launch {
            _deletePlanResult.value = when (val result = cookRepository.deletePlan(id)) {
                is Result.Success -> {
                    Log.d("hank1", "成功刪除，看看result -> $result")
                    getPlanResult(UserManager.user.id,time)
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
        }
    }

    fun getSpecifyManagementResult(planId: String) {
        coroutineScope.launch {
            val result = cookRepository.getSpecifyManagement(planId)
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
            Log.d("hank1","show result => ${result}")
//            Log.d("hank1","show recipes => ${}")
        }
    }


    fun deleteManagement(id: String) {
        coroutineScope.launch {
            when (val result = cookRepository.deleteManagement(id)) {
                is Result.Success -> {
                    Log.d("hank1", "成功刪除，刪除了 -> $id")
//                    getPlanResult(UserManager.user.id,time)
//                    result.data
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
        }
    }


}