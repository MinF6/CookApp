package com.zongmin.cook.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentDetailRecipesBinding
import com.zongmin.cook.databinding.FragmentPlanBinding


class DetailRecipesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailRecipesBinding.inflate(inflater, container, false)


        binding.buttonDetailToEdit.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment())
        }



        return binding.root

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail_recipes, container, false)
    }


}