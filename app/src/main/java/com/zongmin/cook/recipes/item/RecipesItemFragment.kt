package com.zongmin.cook.recipes.item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentRecipesItemBinding
import com.zongmin.cook.ext.getVmFactory
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

        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1", "觸發了變動，我想看recipes ->${it}")
            mCardAdapter!!.remakeData()
            if(it != null) {
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

            Log.d("hank1", "他是null，不給進")
            }


//            mCardShadowTransformer = ShadowTransformer(mViewPager!!, mCardAdapter!!)
//            mCardShadowTransformer!!.enableScaling(true)
//            mViewPager!!.adapter = mCardAdapter
//            mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
//            mViewPager!!.offscreenPageLimit = 3

        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1", "想傳的內容是 -> $it")
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
                viewModel.onDetailNavigated()
            }
        })



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
            viewModel._recipes.value = viewModel._recipes.value
        }


        return binding.root
    }
}
