package com.zongmin.cook.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Management
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
    private var _management = MutableLiveData<List<Management>>()

    val management: LiveData<List<Management>>
        get() = _management

    private var _quantity = MutableLiveData<Int>()

    val quantity: LiveData<Int>
        get() = _quantity

    private var _time = MutableLiveData<Long>()

    val time: LiveData<Long>
        get() = _time


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getManagementResult(userId: String, time: Long) {
        coroutineScope.launch {
            val result = cookRepository.getManagement(userId, time)
            _management.value = when (result) {
                is Result.Success -> {
                    _quantity.value = checkQuantity(result.data)
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

    fun getPeriodManagementResult(userId: String, todayTime: Long, scopeTime: Long) {
        coroutineScope.launch {
            val result = cookRepository.getPeriodManagement(userId, todayTime, scopeTime)

            _management.value = when (result) {
                is Result.Success -> {
                    _quantity.value = checkQuantity(result.data)
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

    private fun checkQuantity(management: List<Management>): Int {
        var quantity = management.size
        for (q in management) {
            if (q.prepare) {
                quantity--
            }
        }

        return quantity
    }

    fun setPrepareResult(isPrepare: Boolean, managementId: String) {
        coroutineScope.launch {
            when (val result = cookRepository.setPrepare(isPrepare, managementId)) {
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

    fun addQuantity() {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun minusQuantity() {
        _quantity.value = _quantity.value?.minus(1)
    }

    private val dayTime = 24 * 60 * 60 * 1000L

    fun getToday(): String {
        setDate(System.currentTimeMillis())
        return SimpleDateFormat(
            "yyyy/MM/dd",
            Locale.getDefault()
        ).format(Date(time.value!!))

    }

    fun getYesterday(): String? {
        _time.value = _time.value?.minus(dayTime)
        return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(time.value!!))
    }

    fun getTomorrow(): String? {
        _time.value = _time.value?.plus(dayTime)
        return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(time.value!!))
    }

    fun getThreeDay(): String {
        setDate(System.currentTimeMillis())
        return SimpleDateFormat(
            "yyyy/MM/dd",
            Locale.getDefault()
        ).format(Date(time.value!!)) + " ~ " + SimpleDateFormat(
            "yyyy/MM/dd", Locale.getDefault()
        ).format(
            Date(time.value!! + (dayTime * 3))
        )
    }

    fun getWeek(): String {
        setDate(System.currentTimeMillis())
        return SimpleDateFormat(
            "yyyy/MM/dd",
            Locale.getDefault()
        ).format(Date(time.value!!)) + " ~ " + SimpleDateFormat(
            "yyyy/MM/dd", Locale.getDefault()
        ).format(
            Date(time.value!! + (dayTime * 7))
        )
    }

    private fun setDate(time: Long) {

        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(time)).toInt()
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(time)).toInt()
        val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date(time)).toInt()
        val storedDate = GregorianCalendar(year, month - 1, day)
        _time.value = storedDate.timeInMillis

    }

}