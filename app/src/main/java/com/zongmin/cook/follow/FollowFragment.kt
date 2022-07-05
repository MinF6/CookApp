package com.zongmin.cook.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner



        // Inflate the layout for this fragment
        return binding.root
    }


}