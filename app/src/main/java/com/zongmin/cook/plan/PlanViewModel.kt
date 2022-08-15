package com.zongmin.cook.plan

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

    private var _plans = MutableLiveData<List<Plan>>()

    val plans: LiveData<List<Plan>>
        get() = _plans

    private var _deletePlanResult = MutableLiveData<String>()

    val deletePlanResult: LiveData<String>
        get() = _deletePlanResult

    private var _managements = MutableLiveData<List<Management>>()

    val managements: LiveData<List<Management>>
        get() = _managements

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _lottie = MutableLiveData<Boolean>()

    val lottie: LiveData<Boolean>
        get() = _lottie

    private val _today = MutableLiveData<Long>()

    val today: LiveData<Long>
        get() = _today

    private val _time = MutableLiveData<Long>()

    val time: LiveData<Long>
        get() = _time

    private var _sortedPlans = MutableLiveData<List<PlanItem>>()

    val sortedPlans: LiveData<List<PlanItem>>
        get() = _sortedPlans

    init {
        setDate()
    }

    private fun setDate() {
        val toDay = System.currentTimeMillis()
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(toDay)).toInt()
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(toDay)).toInt()
        val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date(toDay)).toInt()
        val storedDate = GregorianCalendar(year, month - 1, day)
        _today.value = storedDate.timeInMillis
    }

    fun getPlanResult(userId: String, time: Long) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            _lottie.value = true
            val result = cookRepository.getPlan(userId, time)
            _plans.value = when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
                    _lottie.value = false
                    _time.value = time
                    result.data
                }
                is Result.Fail -> {
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun deletePlan(id: String, time: Long) {
        coroutineScope.launch {
            _deletePlanResult.value = when (val result = cookRepository.deletePlan(id)) {
                is Result.Success -> {
                    getPlanResult(UserManager.user.id, time)
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
            _managements.value = when (result) {
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
        }
    }

    fun deleteManagement(id: String) {
        coroutineScope.launch {
            cookRepository.deleteManagement(id)
        }
    }

    fun setSortedPlan(plans: List<Plan>) {
        val dataList = mutableListOf<PlanItem>()
        val breakfast = mutableListOf<Plan>()
        val lunch = mutableListOf<Plan>()
        val dinner = mutableListOf<Plan>()
        for (plan in plans) {
            when (plan.threeMeals) {
                "早餐" -> {
                    breakfast.add(plan)
                }
                "午餐" -> {
                    lunch.add(plan)
                }
                "晚餐" -> {
                    dinner.add(plan)
                }
            }
        }
        if (breakfast.size > 0) {
            dataList.add(PlanItem.Title("早餐"))
            for (i in breakfast) {
                dataList.add(PlanItem.FullPlan(i))
            }
        }
        if (lunch.size > 0) {
            dataList.add(PlanItem.Title("午餐"))
            for (i in lunch) {
                dataList.add(PlanItem.FullPlan(i))
            }
        }
        if (dinner.size > 0) {
            dataList.add(PlanItem.Title("晚餐"))
            for (i in dinner) {
                dataList.add(PlanItem.FullPlan(i))
            }
        }
        _sortedPlans.value = dataList
    }
}