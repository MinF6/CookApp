package com.zongmin.cook.social

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.User
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SocialViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _recipes = MutableLiveData<List<Recipes>>()

    val recipes: LiveData<List<Recipes>>
        get() = _recipes

    private val _navigateToDetail = MutableLiveData<Recipes>()

    val navigateToDetail: LiveData<Recipes>
        get() = _navigateToDetail

    private var _userMap = MutableLiveData<MutableMap<String, User>>()

    val userMap: LiveData<MutableMap<String, User>>
        get() = _userMap

    private var _userList = MutableLiveData<List<User>>()

    val userList: LiveData<List<User>>
        get() = _userList

    private var _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _like = MutableLiveData<Boolean>()

    val like: LiveData<Boolean>
        get() = _like


    fun getPublicRecipesResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.getPublicRecipes()
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
//            Log.d("hank1","show recipes => ${recipes.value}")
        }
    }

    fun getUserResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.getUser(UserManager.user.id)

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

        }
    }

    private fun getSocialUser(userList: List<String>) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.getSocialUser(userList)
            _userList.value = when (result) {
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

    fun getUserList(recipes: List<Recipes>) {
//        val userList = mutableListOf<String>()
        val userList = mutableSetOf<String>()
        for (i in recipes) {
            userList.add(i.author)
        }
//        Log.d("hank1","看一下整理好的User清單set -> ${userList.toList()}")

        getSocialUser(userList.toList())
    }

    fun setUserMap(userList: List<User>) {
        val map = mutableMapOf<String, User>()
        for (user in userList) {
            map[user.id] = user

        }
        _userMap.value = map
//        Log.d("hank1","看一下整理好的User map -> ${apple}")
//        Log.d("hank1","指定map -> ${apple["EeFC0IAZXET3MSDGP5XsF8k60Gt1"]}")

    }


    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    private fun setCollectResult(isCollect: Boolean, recipesId: String) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.setCollect(isCollect, recipesId)
            when (result) {
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

    private fun setLikeResult(isLiked: Boolean, recipesId: String) {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            val result = cookRepository.setLike(isLiked, recipesId)
             _like.value = when (result) {
//              when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
//                    getPublicRecipesResult()
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

    fun changeCollect(recipesId: String): Boolean {
        return if (UserManager.user.collect.contains(recipesId)) {
            setCollectResult(true, recipesId)
            false
        } else {
            setCollectResult(false, recipesId)
            true
        }
    }

    fun changeLike(recipesId: String, isLiked: Boolean): Boolean {
        return if(isLiked){
            //已經點讚要取消
            setLikeResult(true, recipesId)
            false
        }else{
            setLikeResult(false, recipesId)
            true
        }
    }

//    fun setLike(isLiked: Boolean){
//        _like.value = isLiked
//    }


}