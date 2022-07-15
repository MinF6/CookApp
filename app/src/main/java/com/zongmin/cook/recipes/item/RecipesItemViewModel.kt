package com.zongmin.cook.recipes.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.Plan
import com.zongmin.cook.data.PlanContent
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.recipes.RecipesTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.zongmin.cook.data.Result
import com.zongmin.cook.login.UserManager

class RecipesItemViewModel(
    private val cookRepository: CookRepository,
    recipesType: RecipesTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    private var _recipes = MutableLiveData<List<Recipes>>()

    val recipes: LiveData<List<Recipes>>
        get() = _recipes

    private val _navigateToDetail = MutableLiveData<Recipes?>()

    val navigateToDetail: LiveData<Recipes?>
        get() = _navigateToDetail

    private var _plan = MutableLiveData<Plan>()

    val plan: LiveData<Plan>
        get() = _plan


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //        val type = recipesType.value
    private var key: String = ""

    init {
        getRecipesResult(UserManager.user.collect, recipesType.value)
        Log.d("hank1","查看在RecipesItemViewModel啟動時，UserManager就位沒 -> ${UserManager.user}")
//        getCollectRecipesResult(UserManager.user.collect, recipesType.value)

    }
//    getCollectRecipes

//    private fun getCollectRecipesResult(collect: List<String>, type: String) {
//        coroutineScope.launch {
//            var result: Result<List<Recipes>>? = null
//            if(type == "全部"){
//                result = cookRepository.getCollectRecipes(collect)
//            }else{
//                result = cookRepository.getCategoryRecipes(collect,type)
//            }
//
//
//
//            _recipes.value = when (result) {
//                is Result.Success -> {
//                    result.data
//                }
//                is Result.Fail -> {
//                    null
//                }
//                is Result.Error -> {
//                    null
//                }
//                else -> {
//                    null
//                }
//            }
//        }
//    }




    private fun getRecipesResult(collect: List<String>, type: String) {
        coroutineScope.launch {
            var result: Result<List<Recipes>>? = null
            if (key == "") {
                if (type == "全部") {
                    result = cookRepository.getRecipes(collect)
                } else {
                    result = cookRepository.getCategoryRecipes(collect, type)
                }
            } else {
                if (type == "全部") {
                    result = key?.let { cookRepository.getKeywordRecipes(collect,it) }
                } else {
                    result = key?.let { cookRepository.getCompoundRecipes(collect, type, it) }
                }
            }
            _recipes.value = when (result) {
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
        }
    }

    fun createPlanResult(plan: Plan) {
        coroutineScope.launch {
            when (val result = cookRepository.createPlan(plan)) {
                is Result.Success -> {
                    Log.d("hank1", "成功更新，看看result -> $result")
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


    fun setRecipesKey(type: String, key: String) {
        this.key = key
        getRecipesResult(UserManager.user.collect, type)
    }

    fun setPlan(
        threeMeals: String,
        name: String,
        foodId: String,
        image: String,
        category: String,
        time: Long
    ) {
        val newPlan = Plan("",threeMeals, PlanContent(foodId, image, name, category, time))
        createPlanResult(newPlan)
    }


    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}
