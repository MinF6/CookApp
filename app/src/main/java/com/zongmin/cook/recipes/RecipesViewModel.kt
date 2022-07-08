package com.zongmin.cook.recipes

import android.util.MutableLong
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.data.source.CookRepository
import java.text.SimpleDateFormat
import java.util.*


class RecipesViewModel: ViewModel() {
//class RecipesViewModel(
//    private val cookRepository: CookRepository
//): ViewModel() {

    val searchText = MutableLiveData<String>()

    val threeMeals = MutableLiveData("早餐")

//    val date = MutableLiveData(SimpleDateFormat("MM/dd").format(Date()))
//    val date = MutableLiveData("")

//     var _date = MutableLiveData<String>()
//     var date = MutableLiveData<String>()
     var date = MutableLiveData<Long>()
//     var date = MutableLong(0)

//    val date: LiveData<String>
//        get() = _date


    init {
        getDate()
    }

    private fun getDate(){
//        _date.value = SimpleDateFormat("MM/dd").format(Date())
//        date.value = SimpleDateFormat("MM/dd").format(Date())

        date.value = System.currentTimeMillis()
//        date = System.currentTimeMillis()
    }











}