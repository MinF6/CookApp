package com.zongmin.cook.recipes.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    fun getRecipesResult(type: String ) {
//    fun getRecipesResult() {
//        Log.d("hank1", "現在的type到底是啥 -> $type")
//        Log.d("hank1", "現在的key到底是啥 -> $key")
        coroutineScope.launch {
            var result: Result<List<Recipes>>? = null
//            Log.d("hank1", "現在的type是 -> $type 現在的key是 -> $key")
            if (key == "") {
                if (type == "全部") {
//                    Log.d("hank1", "進了1")
                    result = cookRepository.getRecipes()
//                    Log.d("hank1", "進了1的result為 -> $result")
                } else {
//                    Log.d("hank1", "進了2")
                    result = cookRepository.getCategoryRecipes(type)
//                    result = cookRepository.getCategoryRecipes("蔬菜")
//                    Log.d("hank1", "進了2的result為 -> $result")
                }
            } else {
                if (type == "全部") {
//                    Log.d("hank1", "進了3")
                    result = key?.let { cookRepository.getKeywordRecipes(it) }
//                    Log.d("hank1", "進了3的result為 -> $result")
                } else {
//                    Log.d("hank1", "進了4")
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
//            _recipes.value = _recipes.value
//            Log.d("hank1","show result => ${_result}")
//            Log.d("hank1", "show recipes => ${_recipes.value}")


        }
    }


    fun setRecipesKey(type: String,key: String) {
        this.key = key
        getRecipesResult(type)

        Log.d("hank1", "執行")

    }


    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}
