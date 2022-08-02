package com.zongmin.cook.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.recipes.RecipesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DialogPlanViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private var _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan

    private var _date = MutableLiveData<Long>()

    val date: LiveData<Long>
        get() = _date


    init {
        getToday()
    }

    val dayTime = 24 * 60 * 60 * 1000L

    fun getToday() {
        val newTime = System.currentTimeMillis()
        val storedDate = GregorianCalendar(
            SimpleDateFormat("yyyy").format(Date(newTime)).toInt(),
            SimpleDateFormat("MM").format(Date(newTime)).toInt() - 1,
            SimpleDateFormat("dd").format(Date(newTime)).toInt()
        )
        _date.value = storedDate.timeInMillis

    }

    fun getYesterday() {
        _date.value = _date.value?.minus(dayTime)
    }

    fun getTomorrow() {
        _date.value = _date.value?.plus(dayTime)
    }

}