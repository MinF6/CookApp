package com.zongmin.cook.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentDetailRecipesBinding
import com.zongmin.cook.databinding.FragmentEditRecipesBinding


class EditRecipesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditRecipesBinding.inflate(inflater, container, false)





        return binding.root



        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_recipes, container, false)
    }




}
