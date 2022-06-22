package com.zongmin.cook.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentDialogPlanBinding
import com.zongmin.cook.databinding.FragmentRecipesItemBinding


class DialogPlan : AppCompatDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDialogPlanBinding.inflate(inflater, container, false)




        return binding.root
    }


}