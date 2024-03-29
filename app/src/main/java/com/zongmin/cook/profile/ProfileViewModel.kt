package com.zongmin.cook.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipe
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.network.LoadApiStatus
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

    private var _recipes = MutableLiveData<List<Recipe>>()

    val recipes: LiveData<List<Recipe>>
        get() = _recipes

    private val _navigateToDetail = MutableLiveData<Recipe>()

    val navigateToDetail: LiveData<Recipe>
        get() = _navigateToDetail

    private val _navigateToFollow = MutableLiveData<List<String>>()

    val navigateToFollow: LiveData<List<String>>
        get() = _navigateToFollow

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    init {
        _user.value = UserManager.user
        getRecipesResult()
    }

    private fun getRecipesResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.getCreationRecipes(UserManager.user.id)
            _recipes.value = when (result) {
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
        }
    }

    fun navigateToDetail(recipe: Recipe) {
        _navigateToDetail.value = recipe
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