package com.zongmin.cook.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentProfileBinding
import com.zongmin.cook.databinding.FragmentRecipesBinding


class RecipesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesBinding.inflate(inflater, container, false)


        return binding.root
    }


}