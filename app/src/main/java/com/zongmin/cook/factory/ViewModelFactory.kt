package com.zongmin.cook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.plan.PlanViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val cookRepository: CookRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(PlanViewModel::class.java) ->
                    PlanViewModel(cookRepository)

//                isAssignableFrom(HomeViewModel::class.java) ->
//                    HomeViewModel(stylishRepository)
//
//                isAssignableFrom(CartViewModel::class.java) ->
//                    CartViewModel(stylishRepository)
//
//                isAssignableFrom(PaymentViewModel::class.java) ->
//                    PaymentViewModel(stylishRepository)
//
//                isAssignableFrom(LoginViewModel::class.java) ->
//                    LoginViewModel(stylishRepository)
//
//                isAssignableFrom(CheckoutSuccessViewModel::class.java) ->
//                    CheckoutSuccessViewModel(stylishRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
