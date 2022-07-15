package com.zongmin.cook.social

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Result
import com.zongmin.cook.data.source.CookRepository
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


//    fun getRecipesResult() {
//        coroutineScope.launch {
//            val result = cookRepository.getRecipes()
//            _recipes.value = when (result) {
//                is Result.Success -> {
//                    result.data
//                }
//                is Result.Fail -> {
//                    null
//                }
//                is Result.Error -> {
//
//                    null
//                }
//                else -> {
//
//                    null
//                }
//            }
////            Log.d("hank1","show recipes => ${recipes.value}")
//        }
//    }

    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }



}