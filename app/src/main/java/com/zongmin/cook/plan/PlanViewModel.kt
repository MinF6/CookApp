package com.zongmin.cook.plan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.network.LoadApiStatus
import com.zongmin.cook.util.ServiceLocator.cookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlanViewModel(
    private val cookRepository: CookRepository) : ViewModel() {

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    var _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status





    init {
//        getPlanResult()
//        Log.d("hank1","進到PlanViewModel")
    }

    fun getPlanResult(){

        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = cookRepository.getPlan()
//            Log.d("hank1","show result => ${result}")
            _plan.value = when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
                    Log.d("hank1","成功取得")
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
//            Log.d("hank1","show result => ${result}")
            Log.d("hank1","show recipes => ${plan.value}")
        }
    }

    fun deletePlan(id: String) {
        coroutineScope.launch {
            when (val result = cookRepository.deletePlan(id)) {
                is Result.Success -> {
                    Log.d("hank1", "成功刪除，看看result -> $result")
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



}