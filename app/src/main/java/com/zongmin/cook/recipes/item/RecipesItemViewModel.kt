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

    private val _navigateToDetail = MutableLiveData<Recipes>()

    val navigateToDetail: LiveData<Recipes>
        get() = _navigateToDetail

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getRecipesResult(recipesType.value, null)
//        Log.d("hank1", "看一下拿到的recipesType -> ${recipesType}")
//        Log.d("hank1", "看一下拿到的recipesType.value -> ${recipesType.value}")
//        Log.d("hank1", "看一下拿到的recipesType.ordinal -> ${recipesType.ordinal}")
//        Log.d("hank1", "看一下拿到的recipesType.name -> ${recipesType.name}")
//        Log.d("hank1", "---------------------------------------------------------")

    }


    fun getRecipesResult(type: String, key: String?) {
        Log.d("hank1", "現在的type到底是啥 -> $type")
        Log.d("hank1", "現在的key到底是啥 -> $key")
        coroutineScope.launch {
            var result: Result<List<Recipes>>? = null
//            if(key == null){
//                if (type == "全部") {
//                    result = cookRepository.getRecipes()
//                } else {
//                    result = cookRepository.getCategoryRecipes(type)
//                }
//            }else{
//                if (type == "全部") {
//                    result = cookRepository.getKeywordRecipes(key)
//                } else {
//                    result = cookRepository.getCompoundRecipes(type,key)
//                }
//
//            }
            result = cookRepository.getRecipes()

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
//            Log.d("hank1","show result => ${result}")
//            Log.d("hank1","show recipes => ${recipes.value}")


        }
    }


    fun navigateToDetail(recipes: Recipes) {
        _navigateToDetail.value = recipes
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}
