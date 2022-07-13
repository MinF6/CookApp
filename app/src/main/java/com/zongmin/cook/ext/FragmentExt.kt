package com.zongmin.cook.ext




import android.app.Activity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.zongmin.cook.CookApplication
import com.zongmin.cook.factory.RecipesItemViewModelFactory
import com.zongmin.cook.factory.ViewModelFactory
import com.zongmin.cook.recipes.RecipesTypeFilter

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as CookApplication).cookRepository
    return ViewModelFactory(repository)
}

//fun Activity.getVmFactory(): ViewModelFactory {
//    val repository = (requireContext().applicationContext as CookApplication).cookRepository
//    return ViewModelFactory(repository)
//}
//
//fun Fragment.getVmFactory(user: User?): ProfileViewModelFactory {
//    val repository = (requireContext().applicationContext as StylishApplication).stylishRepository
//    return ProfileViewModelFactory(repository, user)
//}
//
//fun Fragment.getVmFactory(product: Product): ProductViewModelFactory {
//    val repository = (requireContext().applicationContext as StylishApplication).stylishRepository
//    return ProductViewModelFactory(repository, product)
//}

fun Fragment.getVmFactory(recipesType: RecipesTypeFilter): RecipesItemViewModelFactory {
    val repository = (requireContext().applicationContext as CookApplication).cookRepository
    return RecipesItemViewModelFactory(repository, recipesType)
}
