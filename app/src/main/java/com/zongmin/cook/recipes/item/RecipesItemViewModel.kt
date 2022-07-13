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

class RecipesItemViewModel(
    private val cookRepository: CookRepository,
    recipesType: RecipesTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    var _recipes = MutableLiveData<List<Recipes>>()

    val recipes: LiveData<List<Recipes>>
        get() = _recipes

    private val _navigateToDetail = MutableLiveData<Recipes?>()

    val navigateToDetail: LiveData<Recipes?>
        get() = _navigateToDetail

    private var _plan = MutableLiveData<Plan>()

    val plan: LiveData<Plan>
        get() = _plan


//    private val _passKey = MutableLiveData<String>()
//
//    val passKey: LiveData<String>
//        get() = _passKey

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    //        val type = recipesType.value
    private var key: String = ""

    init {
        getRecipesResult(recipesType.value)
//        getRecipesResult()
//        getRecipesResult( null)
//        Log.d("hank1", "看一下拿到的recipesType -> ${recipesType}")
//        Log.d("hank1", "看一下拿到的recipesType.value -> ${recipesType.value}")
//        Log.d("hank1", "看一下拿到的recipesType.ordinal -> ${recipesType.ordinal}")
//        Log.d("hank1", "看一下拿到的recipesType.name -> ${recipesType.name}")
//        Log.d("hank1", "---------------------------------------------------------")

    }


    private fun getRecipesResult(type: String) {
//    fun getRecipesResult() {
//        Log.d("hank1", "現在的type到底是啥 -> $type")
//        Log.d("hank1", "現在的key到底是啥 -> $key")
        coroutineScope.launch {
            var result: Result<List<Recipes>>? = null
//            Log.d("hank1", "現在的type是 -> $type 現在的key是 -> $key")
            if (key == "") {
                if (type == "全部") {
                    result = cookRepository.getRecipes()
//                    Log.d("hank1", "進了1的result為 -> $result")
                } else {
                    result = cookRepository.getCategoryRecipes(type)
//                    result = cookRepository.getCategoryRecipes("蔬菜")
//                    Log.d("hank1", "進了2的result為 -> $result")
                }
            } else {
                if (type == "全部") {
                    result = key?.let { cookRepository.getKeywordRecipes(it) }
//                    Log.d("hank1", "進了3的result為 -> $result")
                } else {
                    result = key?.let { cookRepository.getCompoundRecipes(type, it) }
//                    result = key?.let { cookRepository.getCompoundRecipes("蔬菜", it) }
//                    Log.d("hank1", "進了4的result為 -> $result")
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
        getRecipesResult(type)
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
