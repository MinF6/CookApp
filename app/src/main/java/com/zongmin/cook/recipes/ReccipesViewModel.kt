package com.zongmin.cook.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ReccipesViewModel : ViewModel() {

    val searchText = MutableLiveData<String>()

}