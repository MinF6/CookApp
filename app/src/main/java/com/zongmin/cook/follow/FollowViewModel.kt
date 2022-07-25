package com.zongmin.cook.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FollowViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _user = MutableLiveData<List<User>>()

    val user: LiveData<List<User>>
        get() = _user

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    init {
        getFollowResult(listOf("oAYu4J696CaCdkhBIsWsTizIfhm2","yqU0EW959dbT1CPWZwdHpHzBhU22"))
    }


    fun getFollowResult(userList: List<String>) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.getFollowList(userList)

            _user.value = when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
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

            Log.d("hank1","check2，檢查Query結果，result => $result")


        }
    }


}