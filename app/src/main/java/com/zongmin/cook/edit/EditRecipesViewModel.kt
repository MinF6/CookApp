package com.zongmin.cook.edit

import android.net.Uri
import android.util.Log
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.*
import com.zongmin.cook.data.source.CookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class EditRecipesViewModel(
    private val cookRepository: CookRepository
) : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _recipes = MutableLiveData<Recipes>()

    val recipes: LiveData<Recipes>
        get() = _recipes

    var mainUri = MutableLiveData<Boolean>()

    var itemUri = MutableLiveData<Uri>()
//
//    val mainUri: LiveData<Boolean>
//        get() = _mainUri


    private var _createId = MutableLiveData<String>()

    val createId: LiveData<String>
        get() = _createId


    fun create(summary: Summary, ingredient: List<Ingredient>, step: List<Step>) {
        coroutineScope.launch {
            val result = cookRepository.createRecipes(summary, ingredient, step)
            _createId.value = when (result) {
                is Result.Success -> {
                    Log.d("hank1", "成功更新111，看看result -> $result")

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

    fun getRecipesData(recipes: Recipes) {
        _recipes.value = recipes
    }

    fun selectSpinnerValue(spinner: Spinner, myString: String) {
        val index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == myString) {
                spinner.setSelection(i)
                break
            }
        }
    }


    fun uploadImage() {

    }

    fun deleteRecipes(id: String) {
        coroutineScope.launch {
            when (val result = cookRepository.deleteRecipes(id)) {
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

    fun setCollect(recipesId: String) {

    }


}