package com.zongmin.cook.management

import android.util.Log
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

    var _quantity = MutableLiveData<Int>()

    val quantity: LiveData<Int>
        get() = _quantity

    private var _time = MutableLiveData<Long>()

    val time: LiveData<Long>
        get() = _time


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getManagementResult(userId: String, time: Long) {
//        Log.d("hank1","check1")

        coroutineScope.launch {
            val result = cookRepository.getManagement(userId,time)

//            Log.d("hank1","check2")
            _management.value = when (result) {
                is Result.Success -> {
                    //直接用size會有問題，之後須改成觀察指定欄位判斷
                    _quantity.value = result.data.size
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

    fun getPeriodManagementResult(userId: String, todayTime: Long, scopeTime: Long) {
        Log.d("hank1","區間查詢丟進來的時間scopeTime為 -> $scopeTime")

        coroutineScope.launch {
            val result = cookRepository.getPeriodManagement(userId,todayTime, scopeTime)

//            Log.d("hank1","check2")
            _management.value = when (result) {
                is Result.Success -> {
                    //直接用size會有問題，之後須改成觀察指定欄位判斷
                    _quantity.value = result.data.size
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
            Log.d("hank1","範圍型食材查詢，看看查到了什麼 => ${result}")
//            Log.d("hank1","show recipes => ${recipes.value}")


        }
    }

    fun addQuantity() {
        _quantity.value = _quantity.value?.plus(1)
    }

    fun minusQuantity() {
        _quantity.value = _quantity.value?.minus(1)
    }


//    var showTime = System.currentTimeMillis()
    private val dayTime = 24 * 60 * 60 * 1000L

    //可考慮加星期幾
    fun getToday(): String {
        setDate(System.currentTimeMillis())
        return SimpleDateFormat(
            "yyyy/MM/dd",
            Locale.getDefault()
        ).format(Date(time.value!!))
//        showTime = System.currentTimeMillis()
//        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))


    }

    fun getYesterday(): String? {
//        showTime -= dayTime
//        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
        _time.value = _time.value?.minus(dayTime)
        return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(time.value!!))
    }

    fun getTomorrow(): String? {
        _time.value = _time.value?.plus(dayTime)
        return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date(time.value!!))
    }

    fun getThreeDay(): String {
//        showTime = System.currentTimeMillis()
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
//        showTime = System.currentTimeMillis()
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
//        val today = System.currentTimeMillis()
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(time)).toInt()
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date(time)).toInt()
        val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date(time)).toInt()

        val storedDate = GregorianCalendar(year, month - 1, day)
//        Log.d("hank1", "今天轉換成毫秒是 -> ${storedDate.timeInMillis}")
        _time.value = storedDate.timeInMillis


    }


}