package com.zongmin.cook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.MainViewModel
import com.zongmin.cook.data.source.CookRepository
import com.zongmin.cook.dialog.DialogPlanViewModel
import com.zongmin.cook.edit.EditRecipesViewModel
import com.zongmin.cook.follow.FollowViewModel
import com.zongmin.cook.login.LoginViewModel
import com.zongmin.cook.management.ManagementViewModel
import com.zongmin.cook.plan.PlanViewModel
import com.zongmin.cook.profile.ProfileViewModel
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.social.SocialViewModel

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

                isAssignableFrom(DialogPlanViewModel::class.java) ->
                    DialogPlanViewModel(cookRepository)
//
                isAssignableFrom(ManagementViewModel::class.java) ->
                    ManagementViewModel(cookRepository)
//
                isAssignableFrom(ManagementViewModel::class.java) ->
                    ManagementViewModel(cookRepository)
//
                isAssignableFrom(SocialViewModel::class.java) ->
                    SocialViewModel(cookRepository)
//
                //到時候得拿掉換成吃type版本，同食譜的item
                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(cookRepository)

                isAssignableFrom(RecipesViewModel::class.java) ->
//                    RecipesViewModel(cookRepository)
                    RecipesViewModel()

                isAssignableFrom(EditRecipesViewModel::class.java) ->
                    EditRecipesViewModel(cookRepository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(cookRepository)

                isAssignableFrom(FollowViewModel::class.java) ->
                    FollowViewModel(cookRepository)
//
//                isAssignableFrom(SocialViewModel::class.java) ->
//                    SocialViewModel(cookRepository)
//
//                isAssignableFrom(SocialViewModel::class.java) ->
//                    SocialViewModel(cookRepository)
//
//                isAssignableFrom(SocialViewModel::class.java) ->
//                    SocialViewModel(cookRepository)
//
//                isAssignableFrom(SocialViewModel::class.java) ->
//                    SocialViewModel(cookRepository)
//
//                isAssignableFrom(SocialViewModel::class.java) ->
//                    SocialViewModel(cookRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
