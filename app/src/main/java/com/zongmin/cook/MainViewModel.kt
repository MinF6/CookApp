package com.zongmin.cook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zongmin.cook.util.CurrentFragmentType


class MainViewModel: ViewModel() {

    val currentFragmentType = MutableLiveData<CurrentFragmentType>()



}