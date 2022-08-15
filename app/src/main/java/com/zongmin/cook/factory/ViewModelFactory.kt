package com.zongmin.cook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.detail.DetailRecipesViewModel
import com.zongmin.cook.edit.EditRecipesViewModel
import com.zongmin.cook.follow.FollowViewModel
import com.zongmin.cook.login.LoginViewModel
import com.zongmin.cook.management.ManagementViewModel
import com.zongmin.cook.plan.PlanViewModel
import com.zongmin.cook.profile.ProfileViewModel
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.social.SocialViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val cookRepository: CookRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(PlanViewModel::class.java) ->
                    PlanViewModel(cookRepository)

                isAssignableFrom(ManagementViewModel::class.java) ->
                    ManagementViewModel(cookRepository)

                isAssignableFrom(SocialViewModel::class.java) ->
                    SocialViewModel(cookRepository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(cookRepository)

                isAssignableFrom(RecipesViewModel::class.java) ->
                    RecipesViewModel()

                isAssignableFrom(EditRecipesViewModel::class.java) ->
                    EditRecipesViewModel(cookRepository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(cookRepository)

                isAssignableFrom(FollowViewModel::class.java) ->
                    FollowViewModel(cookRepository)

                isAssignableFrom(DetailRecipesViewModel::class.java) ->
                    DetailRecipesViewModel(cookRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
