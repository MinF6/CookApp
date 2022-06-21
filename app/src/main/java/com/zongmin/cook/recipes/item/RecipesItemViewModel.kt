package com.zongmin.cook.recipes.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
//    recipesType: RecipesTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    var _articles = MutableLiveData<List<Recipes>>()

    val articles: LiveData<List<Recipes>>
        get() = _articles

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
//        getRecipesResult()
    }


    fun getRecipesResult() {
        Log.d("hank1","check1")

        coroutineScope.launch {

            val result = cookRepository.getRecipes()
            Log.d("hank1","check2")
            _articles.value = when (result) {
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
            Log.d("hank1","show result => ${result}")
            Log.d("hank1","show _articles => $articles")
            Log.d("hank1","show articles => ${articles.value}")



        }
    }


}
