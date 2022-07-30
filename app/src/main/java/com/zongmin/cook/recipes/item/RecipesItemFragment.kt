package com.zongmin.cook.recipes.item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.data.Management
import com.zongmin.cook.databinding.FragmentRecipesItemBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.recipes.RecipesTypeFilter
import com.zongmin.viewpagercards.CardPagerAdapter
import com.zongmin.viewpagercards.ShadowTransformer


class RecipesItemFragment(private val recipesType: RecipesTypeFilter) : Fragment() {

    /**
     * Lazily initialize our [CatalogItemViewModel].
     */
    private val viewModel by viewModels<RecipesItemViewModel> { getVmFactory(recipesType) }
    private var mViewPager: ViewPager? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private var mCardShadowTransformer: ShadowTransformer? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesItemBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val recipesViewModel = ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)
        mViewPager = binding.viewPager
        mCardAdapter = CardPagerAdapter(viewModel,recipesViewModel, CardPagerAdapter.OnClickListener {
//            Log.d("hank1","檢查cardItem是 -> $it")
            viewModel.setPlan(recipesViewModel.threeMeals.value!!,it.name,it.id,it.mainImage,it.category,recipesViewModel.date.value!!,it)

        })
//            Log.d("hank1", "現在的type是 -> $recipesType")
        viewModel.planId.observe(viewLifecycleOwner){
            val ingredientList = viewModel.itemRecipe.value?.ingredient
            if (ingredientList != null) {
                for(ingredient in ingredientList){

                    val newManagement = viewModel.itemRecipe.value?.let { it1 ->

                        Management(" ",UserManager.user.id,it,recipesViewModel.threeMeals.value!!,ingredient.ingredientName,
                            it1.name,ingredient.quantity,ingredient.unit,recipesViewModel.date.value!!)
                    }
                    Log.d("hank1","新建的management -> $newManagement")
                    if (newManagement != null) {
                        viewModel.createManagementResult(newManagement)
                    }
                }
            }

        }

        binding.viewModel = viewModel
        viewModel.recipe.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1", "觸發了變動，我想看recipes ->${it}")

            mCardAdapter!!.remakeData()
            if(it != null) {

//                viewModel._status.value = LoadApiStatus.DONE
                binding.imageRecipesNull.visibility = View.GONE
                for (i in it) {
//                Log.d("hank1", "我想看迴圈 ->${i}")
                    mCardAdapter!!.addCardItem(i)
                }
                mCardShadowTransformer = ShadowTransformer(mViewPager!!, mCardAdapter!!)
                mCardShadowTransformer!!.enableScaling(true)
                mViewPager!!.adapter = mCardAdapter
                mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
                mViewPager!!.offscreenPageLimit = 3
//                mCardAdapter!!.submitList(it)
//                mCardAdapter!!.notifyDataSetChanged()

            }else{
                binding.imageRecipesNull.visibility = View.VISIBLE
            Log.d("hank1", "他是null，不給進")

            }


        })

        viewModel.navigateToPlan.observe(viewLifecycleOwner){
            Toast.makeText(context, "安排計畫成功", Toast.LENGTH_LONG).show()
//            findNavController().navigate(NavigationDirections.navigateToPlanFragment())
//            Log.d("hank1", "觸發導航到plan")
        }



        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1", "想傳的內容是 -> $it")
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
                viewModel.onDetailNavigated()
            }
        })

//        viewModel.



//        setFragmentResultListener("RecipesCard${recipesType.value}") { key, bundle ->
//            val result = bundle.getString("bundleKey")
//
//            Log.d("hank1", "-----------------------------------")
////            Log.d("hank1", "我拿到了從父層傳的值 ->$result")
////            viewModel.setRecipesKey(recipesType.value, result)
////            viewModel.setRecipesKey(result!!)
////            Log.d("hank1", "收到傳過來的值後，在fragment當下的recipesType為 -> ${recipesType.value}")
////            viewModel.getRecipesResult(recipesType.value, result)
////            viewModel._recipes.value = viewModel._recipes.value
//        }

        val parentViewModel = ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)
//        Log.d("hank2", "requireParentFragment(), ${requireParentFragment()}")
//        Log.d("hank2", "parentViewModel, ${parentViewModel}")

        parentViewModel.searchText.observe(viewLifecycleOwner) {
//            Log.d("hank2", "------------------------------------------------------------------------")
//            Log.d("hank2", "i'm type [${recipesType.value}], parentViewModel.searchText.observe, it->${it}")
//            viewModel.getRecipesResult(recipesType.value, it)
//            viewModel.getRecipesResult(recipesType.value)
            mCardAdapter!!.remakeData()
            viewModel.setRecipesKey(recipesType.value,it)
//            viewModel._recipes.value = viewModel._recipes.value
        }


        return binding.root
    }
}
