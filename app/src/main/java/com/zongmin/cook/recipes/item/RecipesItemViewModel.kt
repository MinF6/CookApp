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

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var key: String = ""

    init {
        if (UserManager.user.collect.isNotEmpty()) {
            getRecipesResult(UserManager.user.collect, recipesType.value)
        }

    }


    private fun getRecipesResult(collect: List<String>, type: String) {
        coroutineScope.launch {

            var result: Result<List<Recipe>>? = null
            result = if (key == "") {
                if (type == "全部") {
                    cookRepository.getRecipes(collect)
                } else {
                    cookRepository.getCategoryRecipes(collect, type)
                }
            } else {
                if (type == "全部") {
                    key.let { cookRepository.getKeywordRecipes(collect, it) }
                } else {
                    key.let { cookRepository.getCompoundRecipes(collect, type, it) }
                }
            }

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

    private fun createPlanResult(plan: Plan) {
        coroutineScope.launch {
            _planId.value = when (val result = cookRepository.createPlan(plan)) {
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
            PlanContent(foodId, image, name, category)

        )
        _itemRecipe.value = recipe

        createPlanResult(newPlan)
        _navigateToPlan.value = true
    }


    fun navigateToDetail(recipe: Recipe) {
        _navigateToDetail.value = recipe
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}
