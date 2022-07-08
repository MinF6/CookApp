package com.zongmin.cook.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
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

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private var _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan

    private var _date = MutableLiveData<Long>()

    val date: LiveData<Long>
        get() = _date


    init {
//        getPlanResult()
//        Log.d("hank1", "進到DialogPlanViewModel")
        getToday()
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

    fun deletePlan(id: String) {
        coroutineScope.launch {
            when (val result = cookRepository.deletePlan(id)) {
                is Result.Success -> {
                    Log.d("hank1", "成功更新，看看result -> $result")
                    getPlanResult()
                }
                is Result.Fail -> {
                }
                is Result.Error -> {
                }
                else -> {

                }
            }
        }
    }

    var showTime = System.currentTimeMillis()
    val dayTime = 24 * 60 * 60 * 1000L
//    val recipesViewModel = ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)

    //可考慮加星期幾
    fun getToday() {
//        showTime = System.currentTimeMillis()
//        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
        _date.value = System.currentTimeMillis()
    }

    //    fun getYesterday(): String? {
//
//        showTime -= dayTime
////        viewModel.date.value = showTime
////        viewModel.date.value = viewModel.date.value?.minus(dayTime)
////        Log.d("hank1","現在的viewModel.date -> ${viewModel.date.value}")
//        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
//    }

    fun getYesterday() {
        _date.value = _date.value?.minus(dayTime)
    }

    //    fun getTomorrow(): String? {
//        showTime += dayTime
////        viewModel.date.value = showTime
////        viewModel.date.value = viewModel.date.value?.plus(dayTime)
////        Log.d("hank1","現在的viewModel.date -> ${viewModel.date.value}")
//        return SimpleDateFormat("yyyy/MM/dd").format(Date(showTime))
//    }

    fun getTomorrow() {
        _date.value = _date.value?.plus(dayTime)
    }

}