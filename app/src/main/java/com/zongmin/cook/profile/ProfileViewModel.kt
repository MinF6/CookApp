package com.zongmin.cook.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    var _recipes = MutableLiveData<List<Recipes>>()

    val recipes: LiveData<List<Recipes>>
        get() = _recipes

    private val _navigateToDetail = MutableLiveData<Recipes>()

    val navigateToDetail: LiveData<Recipes>
        get() = _navigateToDetail

    private val _navigateToFollow = MutableLiveData<List<String>>()

    val navigateToFollow: LiveData<List<String>>
        get() = _navigateToFollow


    fun getUserResult() {
        coroutineScope.launch {
            val result = cookRepository.getUser(UserManager.user.id)
//            val result2 = cookRepository.getRecipes()
            val result2 = cookRepository.getCreationRecipes(UserManager.user.id)

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

//            Log.d("hank1","check2，檢查Query結果，result => $result")
//            Log.d("hank1","check2，檢查Query結果，result => $result2")
//            Log.d("hank1","show recipes => ${recipes.value}")

        }
    }


    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }


    fun navigateToFollow(user: List<String>) {
        _navigateToFollow.value = user
    }

    fun onFollowNavigated() {
        _navigateToFollow.value = null
    }











}