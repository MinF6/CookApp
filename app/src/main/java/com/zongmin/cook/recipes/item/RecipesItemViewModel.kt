package com.zongmin.cook.recipes.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.*
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.recipes.RecipesTypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.network.LoadApiStatus

class RecipesItemViewModel(
    private val cookRepository: CookRepository,
    recipesType: RecipesTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    private var _recipes = MutableLiveData<List<Recipe>>()

    val recipe: LiveData<List<Recipe>>
        get() = _recipes

    private var _itemRecipe = MutableLiveData<Recipe>()

    val itemRecipe: LiveData<Recipe>
        get() = _itemRecipe

    private var _management = MutableLiveData<Boolean>()

    val management: LiveData<Boolean>
        get() = _management

    private val _navigateToDetail = MutableLiveData<Recipe?>()

    val navigateToDetail: LiveData<Recipe?>
        get() = _navigateToDetail

    private val _navigateToPlan = MutableLiveData<Boolean>()

    val navigateToPlan: LiveData<Boolean>
        get() = _navigateToPlan

    private val _planId = MutableLiveData<String>()

    val planId: LiveData<String>
        get() = _planId

    private var _plan = MutableLiveData<Plan>()

    val plan: LiveData<Plan>
        get() = _plan

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //        val type = recipesType.value
    private var key: String = ""

    init {
        if (UserManager.user.collect.isNotEmpty()) {
            getRecipesResult(UserManager.user.collect, recipesType.value)
        }

//        Log.d("hank1","查看在RecipesItemViewModel啟動時，UserManager就位沒 -> ${UserManager.user}")
//        getCollectRecipesResult(UserManager.user.collect, recipesType.value)

    }


    private fun getRecipesResult(collect: List<String>, type: String) {
        coroutineScope.launch {
//            _status.value = LoadApiStatus.LOADING

            var result: Result<List<Recipe>>? = null
            if (key == "") {
                if (type == "全部") {
                    result = cookRepository.getRecipes(collect)
                } else {
                    result = cookRepository.getCategoryRecipes(collect, type)
                }
            } else {
                if (type == "全部") {
                    result = key?.let { cookRepository.getKeywordRecipes(collect, it) }
                } else {
                    result = key?.let { cookRepository.getCompoundRecipes(collect, type, it) }
                }
            }
            if (result == null) {
//                Log.d("hank1","7777777777777")
//                _status.value = LoadApiStatus.ERROR
            } else {
//                Log.d("hank1","88888888888888")
            }
            _recipes.value = when (result) {
                is Result.Success -> {
                    _status.value = LoadApiStatus.DONE
//                    Log.d("hank1","11111111111111")
                    result.data

                }
                is Result.Fail -> {
                    _status.value = LoadApiStatus.ERROR
//                    Log.d("hank1","22222222222222222")
                    null
                }
                is Result.Error -> {
                    _status.value = LoadApiStatus.ERROR
//                    Log.d("hank1","3333333333333333")
                    null
                }
                else -> {
                    _status.value = LoadApiStatus.ERROR
//                    Log.d("hank1","44444444444444")
                    null
                }
            }
//            Log.d("hank1","查詢回來的內容 -> $result")
        }
    }

    private fun createPlanResult(plan: Plan) {
        coroutineScope.launch {
//            _navigateToPlan.value = when (val result = cookRepository.createPlan(plan)) {
            _planId.value = when (val result = cookRepository.createPlan(plan)) {
                is Result.Success -> {
//                    Log.d("hank1", "成功更新，看看result -> $result")
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

    fun createManagementResult(management: Management) {
        coroutineScope.launch {
            _management.value = when (val result = cookRepository.createManagement(management)) {
                is Result.Success -> {
                    Log.d("hank1", "成功更新，看看result -> $result")
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
        time: Long,
        recipe: Recipe
    ) {
        val newPlan = Plan(
            "",
            UserManager.user.id,
            threeMeals,
            time,
//            PlanContent(foodId, image, name, category, time)
            PlanContent(foodId, image, name, category)

        )
//        Log.d("hank1","創建烹飪計畫的系統毫秒 -> ${System.currentTimeMillis()}")
        _itemRecipe.value = recipe

//        createPlanResult(newPlan)
        createPlanResult(newPlan)
//        setManagement(id)

        _navigateToPlan.value = true
    }

    fun setManagement(
        planId: String
    ) {
//        val recipe = itemRecipe.value
//        val newManagement =
//            Management("", UserManager.user.id,planId,)


//            createManagementResult(newManagement)


    }


    fun navigateToDetail(recipe: Recipe) {
        _navigateToDetail.value = recipe
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}
