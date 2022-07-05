package com.zongmin.cook.edit

import android.text.Editable
import android.util.Log
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


    fun create(summary: Summary, ingredient: List<Ingredient>,step: List<Step>) {
        coroutineScope.launch {
            when (val result = cookRepository.createRecipes(summary, ingredient, step)) {
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













}