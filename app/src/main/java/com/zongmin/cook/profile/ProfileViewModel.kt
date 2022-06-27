package com.zongmin.cook.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    var _recipes = MutableLiveData<List<Recipes>>()

    val recipes: LiveData<List<Recipes>>
        get() = _recipes


    fun getUserResult() {
        coroutineScope.launch {
            val result = cookRepository.getUser()
            val result2 = cookRepository.getRecipes()

            _user.value = when (result) {
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
            //到時候要改成針對ID的query
            _recipes.value = when (result2) {
                is Result.Success -> {
                    result2.data
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

            Log.d("hank1","check2，檢查Query結果，result => $result")
            Log.d("hank1","check2，檢查Query結果，result => $result2")
//            Log.d("hank1","show recipes => ${recipes.value}")

        }
    }

















}