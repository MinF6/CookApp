package com.zongmin.cook.social

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentRecipesBinding
import com.zongmin.cook.databinding.FragmentSocialBinding


class SocialFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSocialBinding.inflate(inflater, container, false)


        return binding.root
    }


}